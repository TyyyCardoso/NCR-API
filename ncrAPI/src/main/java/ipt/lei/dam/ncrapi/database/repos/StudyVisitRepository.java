package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.StudyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudyVisitRepository extends JpaRepository<StudyVisit, Integer> {
    @Override
    List<StudyVisit> findAll();
}
