package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(email);
    }

    public void createNewUser(User user){
        userRepository.save(user);
    }




}
