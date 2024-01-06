package ipt.lei.dam.ncrapi.database.repos;

import ipt.lei.dam.ncrapi.database.entities.DidYouKnow;
import ipt.lei.dam.ncrapi.database.entities.Docs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocsRepository extends JpaRepository<Docs, Integer> {
    @Override
    List<Docs> findAll();
}
