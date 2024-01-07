package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.Docs;
import ipt.lei.dam.ncrapi.database.repos.DocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocsService {

    @Autowired
    DocsRepository docsRepository;

    public List<Docs> getAllDocs(){
        return docsRepository.findAll();
    }

    public Docs saveDoc(Docs doc){
        return docsRepository.save(doc);
    }
}
