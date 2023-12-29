package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { return userRepository.getUserByEmail(email);}

    public void createNewUser(User user){ userRepository.save(user);}

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> getAdministrators(){
        return userRepository.getUsersByUserType("9");
    }

    public User getUserByID(Integer userID) {
        return userRepository.getUserById(userID);
    }

    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Integer userID, String newPassword) {
        User user = getUserByID(userID);
        if (user != null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
        }
    }

    @Transactional
    public void validateUser(Integer userID) {
        User user = getUserByID(userID);
        if (user != null) {
            user.setValidated(true);
            userRepository.save(user);
        }
    }




}
