package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.Contest;
import ipt.lei.dam.ncrapi.database.entities.DidYouKnow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DidYouKnowRepository extends JpaRepository<DidYouKnow, Integer> {
    @Override
    List<DidYouKnow> findAll();
}

