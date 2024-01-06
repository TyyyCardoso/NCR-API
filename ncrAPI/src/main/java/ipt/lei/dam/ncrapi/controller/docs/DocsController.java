package ipt.lei.dam.ncrapi.controller.docs;

import ipt.lei.dam.ncrapi.database.entities.Docs;
import ipt.lei.dam.ncrapi.database.services.DidYouKnowService;
import ipt.lei.dam.ncrapi.database.services.DocsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/docs")
public class DocsController {

    @Autowired
    private DocsService docsService;

    @GetMapping("/all")
    public ResponseEntity docs() {
        List<Docs> documents = docsService.getAllDocs();
        return ResponseEntity.ok(documents);
    }


}
