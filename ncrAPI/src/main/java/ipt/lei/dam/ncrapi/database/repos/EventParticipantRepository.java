package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Integer> {
    @Override
    List<EventParticipant> findAll();
}

