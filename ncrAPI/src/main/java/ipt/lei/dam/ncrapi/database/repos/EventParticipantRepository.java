package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.entities.EventParticipant;
import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Integer> {
    @Override
    List<EventParticipant> findAll();

    @Override
    <S extends EventParticipant> S save(S entity);

    @Modifying
    @Transactional
    @Query("DELETE FROM EventParticipant s WHERE s.event = :event")
    void deleteAllByEventId(Event event);

    @Query("SELECT s FROM EventParticipant s WHERE s.event = :event AND s.user = :user AND s.canceled = :canceled")
    EventParticipant findSusbcribedEventByUser(Event event, User user, boolean canceled);

    @Modifying
    @Transactional
    @Query("UPDATE EventParticipant s SET s.canceled = true WHERE s.event = :event AND s.user = :user")
    int cancelSubscription(Event event, User user);
}

