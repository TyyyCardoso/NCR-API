package ipt.lei.dam.ncrapi.controller;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/users")
    public List<User> getAllUsers() {
        //test return all users
        System.out.println(userService.getAllUsers());
        return userService.getAllUsers();
    }



}
