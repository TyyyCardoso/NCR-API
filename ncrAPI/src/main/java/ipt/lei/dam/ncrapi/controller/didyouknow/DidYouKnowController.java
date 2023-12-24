/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipt.lei.dam.ncrapi.controller.didyouknow;

import ipt.lei.dam.ncrapi.database.services.DidYouKnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
