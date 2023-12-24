/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.DidYouKnow;
import ipt.lei.dam.ncrapi.database.repos.DidYouKnowRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DidYouKnowService {
     @Autowired
     DidYouKnowRepository didYouKnowRepository;
     
     public List<DidYouKnow> getAllDidYouKnows(){
         return didYouKnowRepository.findAll();
     }
     
}
