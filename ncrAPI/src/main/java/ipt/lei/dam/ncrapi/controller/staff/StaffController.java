package ipt.lei.dam.ncrapi.controller.staff;

import ipt.lei.dam.ncrapi.database.entities.Staff;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.services.StaffService;
import ipt.lei.dam.ncrapi.database.services.UserService;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;
import ipt.lei.dam.ncrapi.dto.editprofile.EditProfileDTO;
import ipt.lei.dam.ncrapi.dto.staff.StaffResponseDTO;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/all")
    public ResponseEntity getStaff() {

        List<StaffResponseDTO> staffResponseDTOList = new ArrayList<>();

        List<Staff> staff = staffService.getStaff();

        if(staff==null){
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        for(Staff membro : staff){
            StaffResponseDTO staffMemberInfo = new StaffResponseDTO(membro.getUser().getName(), membro.getUser().getEmail(), membro.getUser().getImage(), membro.getCargo(), membro.getDescricao(), membro.getTelefone(), membro.getCodpais(), membro.getStatus(), membro.getDataEntrada().toString());
            staffResponseDTOList.add(staffMemberInfo);
        }

        return ResponseEntity.ok(staffResponseDTOList);
    }

}
