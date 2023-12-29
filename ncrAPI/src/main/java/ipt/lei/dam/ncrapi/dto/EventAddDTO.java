/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipt.lei.dam.ncrapi.dto;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author pcjoa
 */
public record EventAddDTO(String name, String description, String date, String location, Boolean transport, MultipartFile image){}
