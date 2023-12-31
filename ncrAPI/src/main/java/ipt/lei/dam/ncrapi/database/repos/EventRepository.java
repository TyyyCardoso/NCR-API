package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Override
    List<Event> findAll();

    @Query("SELECT s FROM Event s INNER JOIN EventParticipant ep ON s = ep.event WHERE ep.user = :user AND ep.canceled = false  ORDER BY s.createdAt DESC")
    List<Event> findAllSubscribed(User user);

    @Override
    Optional<Event> findById(Integer integer);
}
