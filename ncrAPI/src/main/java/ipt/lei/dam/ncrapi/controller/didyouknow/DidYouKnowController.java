/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipt.lei.dam.ncrapi.controller.didyouknow;

import ipt.lei.dam.ncrapi.database.entities.DidYouKnow;
import ipt.lei.dam.ncrapi.database.services.DidYouKnowService;
import ipt.lei.dam.ncrapi.dto.DefaultResponseDTO;
import ipt.lei.dam.ncrapi.dto.DidYouKnowAddDTO;
import ipt.lei.dam.ncrapi.dto.DidYouKnowEditDTO;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/didyouknow")
public class DidYouKnowController {

    @Autowired
    private DidYouKnowService didYouKnowService;

    @GetMapping("/all")
    public ResponseEntity didYouKnow() {
        return ResponseEntity.ok(didYouKnowService.getAllDidYouKnows());
    }

    @PostMapping
    public ResponseEntity addDidYouKnow(@RequestBody DidYouKnowAddDTO didYouKnowDTO) {
        DidYouKnow didYouKnow = new DidYouKnow();
        didYouKnow.setTitle(didYouKnowDTO.title());
        didYouKnow.setText(didYouKnowDTO.text());
        didYouKnow.setReferences(didYouKnowDTO.references());
        didYouKnow.setCreatedAt(LocalDateTime.now());
        didYouKnow.setUpdatedAt(LocalDateTime.now());
        
        didYouKnowService.addDidYouKnow(didYouKnow);

        return ResponseEntity.ok().body(new DefaultResponseDTO(200, "Sabias que criado com sucesso"));
    }

    @PutMapping
    public ResponseEntity editDidYouKnow(@RequestBody DidYouKnowEditDTO didYouKnowDTO) {
        Optional<DidYouKnow> didYouKnow = didYouKnowService.getDidYouKnow(didYouKnowDTO.id());
        
        if(didYouKnow.isPresent()){
            DidYouKnow didYouKnowToEdit = didYouKnow.get();
            
            if(!didYouKnowDTO.title().isEmpty()){
                didYouKnowToEdit.setTitle(didYouKnowDTO.title());
            }
            
            if(!didYouKnowDTO.text().isEmpty()){
                didYouKnowToEdit.setText(didYouKnowDTO.text());
            }
            
            if(!didYouKnowDTO.references().isEmpty()){
                didYouKnowToEdit.setReferences(didYouKnowDTO.references());
            }
            
            didYouKnowToEdit.setUpdatedAt(LocalDateTime.now());
            
            didYouKnowService.addDidYouKnow(didYouKnowToEdit);
            
            return ResponseEntity.ok().body(new DefaultResponseDTO(200, "Sabias que editado com sucesso"));
        } else {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_DIDYOUKNOW;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }
        
        

       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDidYouKnow(@PathVariable int id) {
        return ResponseEntity.ok(didYouKnowService.deleteDidYouKnow(id));
    }
}
