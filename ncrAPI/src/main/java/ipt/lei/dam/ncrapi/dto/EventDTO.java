/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipt.lei.dam.ncrapi.dto;

import java.time.LocalDateTime;

/**
 *
 * @author pcjoa
 */
public record EventDTO(int id ,String name, String description, String date, String location, Boolean transport, String createdAt, String updatedAt, String image){}
