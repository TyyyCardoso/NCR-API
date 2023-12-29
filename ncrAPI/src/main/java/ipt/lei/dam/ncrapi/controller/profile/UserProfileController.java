package ipt.lei.dam.ncrapi.controller.profile;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.services.UserService;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;
import ipt.lei.dam.ncrapi.dto.editprofile.EditProfileDTO;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody EditProfileDTO editProfileDTO) {

        User user = userService.getUserByEmail(editProfileDTO.email());

        if(user==null){
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        if(!editProfileDTO.newName().isEmpty()){
            user.setName(editProfileDTO.newName());
        }

        if(!editProfileDTO.newImage().isEmpty()){
            user.setImage(editProfileDTO.newImage());
        }

        if(!editProfileDTO.newAbout().isEmpty()){
            user.setAbout(editProfileDTO.newAbout());
        }

        userService.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/administrator")
    public ResponseEntity getAdministrators() {

        List<User> user = userService.getAdministrators();

        if(user==null){
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        return ResponseEntity.ok(user);
    }

}