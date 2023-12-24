/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipt.lei.dam.ncrapi.controller.didyouknow;

import ipt.lei.dam.ncrapi.database.entities.DidYouKnow;
import ipt.lei.dam.ncrapi.database.services.DidYouKnowService;
import ipt.lei.dam.ncrapi.dto.DidYouKnowDTO;
import java.time.LocalDateTime;
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
     public ResponseEntity didYouKnow(){
         return ResponseEntity.ok(didYouKnowService.getAllDidYouKnows());
     }
     
     @PostMapping
     public ResponseEntity addDidYouKnow(@RequestBody DidYouKnowDTO didYouKnowDTO){
         DidYouKnow didYouKnow = new DidYouKnow();
         didYouKnow.setTitle(didYouKnowDTO.title());
         didYouKnow.setText(didYouKnowDTO.text());
         didYouKnow.setReferences(didYouKnowDTO.references());
         didYouKnow.setCreatedAt(LocalDateTime.now());
         didYouKnow.setUpdatedAt(LocalDateTime.now());
         
         return ResponseEntity.ok(didYouKnowService.addDidYouKnow(didYouKnow));
     }
     
     @PutMapping
     public ResponseEntity editDidYouKnow(@RequestBody DidYouKnowDTO didYouKnowDTO){
         DidYouKnow didYouKnow = new DidYouKnow();
         didYouKnow.setId(didYouKnowDTO.id());
         didYouKnow.setTitle(didYouKnowDTO.title());
         didYouKnow.setText(didYouKnowDTO.text());
         didYouKnow.setReferences(didYouKnowDTO.references());
         didYouKnow.setCreatedAt(LocalDateTime.parse(didYouKnowDTO.createdAt()));;
         didYouKnow.setUpdatedAt(LocalDateTime.now());
         
         return ResponseEntity.ok(didYouKnowService.addDidYouKnow(didYouKnow));
     }
     
     @DeleteMapping("/{id}")
      public ResponseEntity deleteDidYouKnow(@PathVariable int id){
          return ResponseEntity.ok(didYouKnowService.deleteDidYouKnow(id));
      }
}
