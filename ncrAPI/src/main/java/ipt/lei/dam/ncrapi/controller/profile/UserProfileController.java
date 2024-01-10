package ipt.lei.dam.ncrapi.controller.profile;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.services.UserService;
import ipt.lei.dam.ncrapi.dto.DefaultResponseDTO;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;
import ipt.lei.dam.ncrapi.dto.editprofile.EditProfileDTO;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    String projectRoot = System.getProperty("user.dir");
    String uploadDir = projectRoot + "/src/main/resources/files/images";

    @Autowired
    private UserService userService;

    @PostMapping("/edit")
    public ResponseEntity edit(
            @RequestParam("name") String newName,
            @RequestParam("about") String newAbout,
            @RequestParam("email") String email,
            @RequestParam(value = "image", required = false) MultipartFile newImage) {
        
        System.out.println("Inside profile editting");

        User user = userService.getUserByEmail(email);

        if (user == null) {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        try {
            if (!newName.isEmpty()) {
                user.setName(newName);
            }

            if (newImage != null && !newImage.isEmpty()) {
                String imageFileNameFinal = UUID.randomUUID().toString() + ".jpg";

                File file = new File(uploadDir + "/" + imageFileNameFinal);
                if (!file.exists()) {
                    file.mkdirs(); // criar diretoria se nao existir
                }
                newImage.transferTo(file);
                user.setImage(imageFileNameFinal);
            }

            if (!newAbout.isEmpty()) {
                user.setAbout(newAbout);
            }

            userService.save(user);
            
            if(null != user.getImage()){
                System.out.println("returning with image name: " + user.getImage());
                return ResponseEntity.ok().body(new DefaultResponseDTO(200, user.getImage()));
            }
             
            else{
                System.out.println("returning no image name");
                return ResponseEntity.ok().build();
            }
                
            
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Imagem demasiado grande!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
        }

    }

    @GetMapping("/administrator")
    public ResponseEntity getAdministrators() {

        List<User> user = userService.getAdministrators();

        if (user == null) {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        return ResponseEntity.ok(user);
    }

}
