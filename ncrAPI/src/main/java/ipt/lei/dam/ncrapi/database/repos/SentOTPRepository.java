package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.Lecture;
import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SentOTPRepository extends JpaRepository<SentOTP, Integer> {

    @Override
    <S extends SentOTP> S save(S entity);

    @Query("SELECT s FROM SentOTP s WHERE s.userId = :userId ORDER BY s.createdAt DESC LIMIT 1")
    Optional<SentOTP> findLastOTPByUserID(int userId);
}
