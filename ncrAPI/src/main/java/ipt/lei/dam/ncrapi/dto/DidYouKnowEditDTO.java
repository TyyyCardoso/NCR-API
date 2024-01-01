/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package ipt.lei.dam.ncrapi.dto;

/**
 *
 * @author pcjoa
 */
public record DidYouKnowEditDTO(
        int id,
        String title,
        String text,
        String references, 
        String createdAt) {}
