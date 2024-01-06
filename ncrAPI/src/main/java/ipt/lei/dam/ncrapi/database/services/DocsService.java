package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.Docs;
import ipt.lei.dam.ncrapi.database.repos.DocsRepository;
import ipt.lei.dam.ncrapi.database.repos.EventRepository;
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
}
