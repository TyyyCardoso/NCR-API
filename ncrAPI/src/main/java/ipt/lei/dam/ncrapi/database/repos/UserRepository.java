package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.entities.User;
import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Gets user by email
    User getUserByEmail(String email);

    User getUserById(Integer id);
    
    @Override
    <S extends User> S save(S entity);

}

