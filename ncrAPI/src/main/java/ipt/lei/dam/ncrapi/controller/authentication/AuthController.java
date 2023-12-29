package ipt.lei.dam.ncrapi.controller.authentication;

import ipt.lei.dam.ncrapi.config.LoggingInterceptor;
import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.services.SentOTPService;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;
import ipt.lei.dam.ncrapi.dto.change.ChangePasswordDTO;
import ipt.lei.dam.ncrapi.dto.login.LoginDTO;
import ipt.lei.dam.ncrapi.dto.recover.RecoverPasswordDTO;
import ipt.lei.dam.ncrapi.dto.register.RegisterDTO;
import ipt.lei.dam.ncrapi.dto.login.LoginResponseDTO;
import ipt.lei.dam.ncrapi.dto.register.RegisterResponseDTO;
import ipt.lei.dam.ncrapi.dto.send.SendOTPDTO;
import ipt.lei.dam.ncrapi.dto.validate.ValidateOTPDTO;
import ipt.lei.dam.ncrapi.dto.validate.ValidateOTPResponseDTO;
import ipt.lei.dam.ncrapi.config.security.TokenService;
import ipt.lei.dam.ncrapi.database.services.UserService;
import ipt.lei.dam.ncrapi.services.EmailService;
import ipt.lei.dam.ncrapi.services.OTPService;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import ipt.lei.dam.ncrapi.utils.enums.UserRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SentOTPService sentOTPService;
    @Autowired
    private OTPService otpService;
    @Autowired
    private EmailService emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDTO loginRequest) {
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            String token = tokenService.generateToken((User) authentication.getPrincipal());

            LOGGER.warn("MAKING TOKEN ON LOGIN: " + token);

            User loggedUser = userService.getUserByEmail(loginRequest.email());

            if(!loggedUser.isValidated()){
                String otpCode = otpService.generateOTPCode();
                SentOTP sentOTP = new SentOTP(loggedUser.getId(), otpCode);
                sentOTPService.createNewOTP(sentOTP);

                emailService.sendConfirmAccountEmail(loggedUser.getEmail(), otpCode);
            }

            String userType = UserRoles.getRoleByID(Integer.parseInt(loggedUser.getUserType()));

            return ResponseEntity.ok(new LoginResponseDTO(token, loggedUser.isValidated(), loggedUser.getEmail(), loggedUser.getName(), userType, loggedUser.getRegistrationDate().toString(), loggedUser.getImage(), loggedUser.getAbout()));
        } catch (BadCredentialsException e) {
            ErrorsEnum error = ErrorsEnum.INVALID_CREDENTIALS;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        } catch (InternalAuthenticationServiceException e) {
            ErrorsEnum error = ErrorsEnum.INVALID_CREDENTIALS;
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        } catch (DisabledException e) {
            ErrorsEnum error = ErrorsEnum.ACCOUNT_LOCKED;
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        } catch (Exception e) {
            ErrorsEnum error = ErrorsEnum.UNKNOWN_ERROR;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO registerRequest){

        if(!AuthBO.validateUserEmail(registerRequest.email()))
            return ResponseEntity.badRequest().build();
        if(userService.loadUserByUsername(registerRequest.email()) != null){
            ErrorsEnum error = ErrorsEnum.EMAIL_EXISTS_ERROR;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }


        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.name(), registerRequest.email(), encryptedPassword);

        userService.createNewUser(newUser);

        try{
            String otpCode = otpService.generateOTPCode();
            SentOTP sentOTP = new SentOTP(newUser.getId(), otpCode);
            sentOTPService.createNewOTP(sentOTP);

            emailService.sendConfirmAccountEmail(newUser.getEmail(), otpCode);
        }catch(Exception e){
            ErrorsEnum error = ErrorsEnum.UNKNOWN_ERROR;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        return ResponseEntity.ok().body(new RegisterResponseDTO(newUser.getEmail()));
    }

    @PostMapping("/recover")
    public ResponseEntity recover(@RequestBody @Validated RecoverPasswordDTO recoverPasswordRequest){
        ErrorsEnum error = ErrorsEnum.SEND_OTP_ERROR;

        if(!AuthBO.validateUserEmail(recoverPasswordRequest.email()))
            return ResponseEntity.badRequest().build();

        User user = userService.getUserByEmail(recoverPasswordRequest.email());

        try{
            if(user!=null){
                boolean sendMail = true;
                String otpCode = otpService.generateOTPCode();

                Optional<SentOTP> lastSentOTP =  sentOTPService.getLastSentOTP(user.getId());
                if(lastSentOTP.isPresent()){
                    SentOTP lastOTP = lastSentOTP.get();

                    if(!lastOTP.getIsUsed()){
                        LocalDateTime currentTime = LocalDateTime.now();
                        LocalDateTime OTPExpirationTime = lastOTP.getExpiresAt();

                        if(currentTime.isBefore(OTPExpirationTime)){
                            sendMail = false;
                        }
                    }
                }

                if(sendMail){
                    SentOTP sentOTP = new SentOTP(user.getId(), otpCode);
                    sentOTPService.createNewOTP(sentOTP);

                    emailService.sendRecoverPasswordEmail(user.getEmail(), otpCode);

                    return ResponseEntity.ok().build();
                }
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public ResponseEntity validate(@RequestBody @Validated ValidateOTPDTO validateOTPDTO){
        ErrorsEnum error = ErrorsEnum.VALIDATE_OTP_ERROR;

        User user = userService.getUserByEmail(validateOTPDTO.email());

        if(user!=null){
            Optional<SentOTP> lastSentOTP = sentOTPService.getLastSentOTP(user.getId());
            if(lastSentOTP.isPresent()){
                SentOTP lastOTP = lastSentOTP.get();

                if(!lastOTP.getIsUsed()){
                    LocalDateTime currentTime = LocalDateTime.now();
                    LocalDateTime OTPExpirationTime = lastOTP.getExpiresAt();

                    if(currentTime.isBefore(OTPExpirationTime)){
                        if(validateOTPDTO.otp().equals(lastOTP.getOtpCode())){
                            sentOTPService.useOTP(lastOTP.getOtpId());
                            if (!validateOTPDTO.type().equals("1")) {
                                userService.validateUser(user.getId());
                            }
                            return ResponseEntity.ok().body(new ValidateOTPResponseDTO(user.getId()));

                        }
                    }
                }
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
    }

    @PostMapping("/change")
    public ResponseEntity change(@RequestBody @Validated ChangePasswordDTO changePasswordDTO){

        try{
            userService.updatePassword(changePasswordDTO.userID(), changePasswordDTO.password());
        }catch(Exception e){
            ErrorsEnum error = ErrorsEnum.CHANGE_PASSWORD_ERROR;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity send(@RequestBody @Validated SendOTPDTO sendOTPDTO){
        try{

            User user = userService.getUserByEmail(sendOTPDTO.email());

            if(user!=null){
                Optional<SentOTP> lastSentOTP = sentOTPService.getLastSentOTP(user.getId());
                if(lastSentOTP.isPresent()){
                    SentOTP lastOTP = lastSentOTP.get();

                    if(!lastOTP.getIsUsed()){
                        sentOTPService.useOTP(lastOTP.getOtpId());
                    }

                    String otpCode = otpService.generateOTPCode();

                    SentOTP sentOTP = new SentOTP(user.getId(), otpCode);
                    sentOTPService.createNewOTP(sentOTP);

                    emailService.sendRecoverPasswordEmail(user.getEmail(), otpCode);
                }
            }

            return ResponseEntity.ok().build();
        }catch(Exception e){
            ErrorsEnum error = ErrorsEnum.SEND_OTP_ERROR;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }
    }


}
