package ipt.lei.dam.ncrapi.services;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
