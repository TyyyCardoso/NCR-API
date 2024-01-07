package ipt.lei.dam.ncrapi.controller.docs;

import ipt.lei.dam.ncrapi.config.LoggingInterceptor;
import ipt.lei.dam.ncrapi.database.entities.Docs;
import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.services.DidYouKnowService;
import ipt.lei.dam.ncrapi.database.services.DocsService;
import ipt.lei.dam.ncrapi.dto.DefaultResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/docs")
public class DocsController {

    @Autowired
    private DocsService docsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);
    String projectRoot = System.getProperty("user.dir");
    String uploadDir = projectRoot + "/webapps/ncrAPI/WEB-INF/classes/static/files/docs";

    @GetMapping("/all")
    public ResponseEntity docs() {
        List<Docs> documents = docsService.getAllDocs();
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocs(@PathVariable int id) {
        docsService.deleteDoc(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/uploadSchedule")
    public ResponseEntity uploadSchedule(
            @RequestParam("docName") String docName,
            @RequestParam("docDescription") String docDescription,
            @RequestParam("docType") String docType,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf) {

        try {
            // Salvar os outros dados do evento no banco de dados
            Docs doc = new Docs();
            doc.setDocName(docName);
            doc.setDocDescription(docDescription);
            doc.setDocType(Integer.parseInt(docType));

            if (pdf != null && !pdf.isEmpty()) {

                // Gerar nome aleatorio para imagem
                String fileName = UUID.randomUUID() + ".pdf";

                File file = new File(uploadDir + "/" + fileName);
                LOGGER.warn(uploadDir + "/" + fileName);
                if (!file.exists()) {
                    LOGGER.warn("A criar");
                    file.mkdirs(); // criar diretoria se nao existir
                }
                pdf.transferTo(file);

                doc.setDocReference(fileName);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ficheiro inválido!");
            }

            docsService.saveDoc(doc);

            return ResponseEntity.ok().body(new DefaultResponseDTO(200, "Horário criado com sucesso"));
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Imagem demasiado grande!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
        }
    }


}
