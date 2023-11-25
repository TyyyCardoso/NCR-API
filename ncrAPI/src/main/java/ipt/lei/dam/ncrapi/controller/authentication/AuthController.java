package ipt.lei.dam.ncrapi.controller;

import ipt.lei.dam.ncrapi.bo.AuthBO;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.dto.login.LoginDTO;
import ipt.lei.dam.ncrapi.dto.RegisterDTO;
import ipt.lei.dam.ncrapi.dto.login.LoginResponseDTO;
import ipt.lei.dam.ncrapi.infra.security.TokenService;
import ipt.lei.dam.ncrapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDTO loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO registerRequest){

        if(!AuthBO.validateUserEmail(registerRequest.email()))
            return ResponseEntity.badRequest().build();
        if(userService.loadUserByUsername(registerRequest.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.name(), registerRequest.email(), encryptedPassword);

        userService.createNewUser(newUser);

        return ResponseEntity.ok().build();

    }


}
