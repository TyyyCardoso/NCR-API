package ipt.lei.dam.ncrapi.controller.events;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.entities.EventParticipant;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.services.EventService;
import ipt.lei.dam.ncrapi.database.services.UserService;
import ipt.lei.dam.ncrapi.dto.DefaultResponseDTO;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ipt.lei.dam.ncrapi.dto.EventsDTO;
import ipt.lei.dam.ncrapi.dto.subscribe.SubscribeEventDTO;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    String projectRoot = System.getProperty("user.dir");
    String uploadDir = projectRoot + "/src/main/resources/files/images";

    @PostMapping("/all")
    public ResponseEntity events(@RequestBody EventsDTO eventsDTO) {

        if (eventsDTO.email().isEmpty()) {
            return ResponseEntity.ok(eventService.getAllEvents());
        }

        List<Event> listaEventos = eventService.getAllEvents();

        User user = userService.getUserByEmail(eventsDTO.email());

        if (user == null) {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        List<Event> listaEventosSubscribed = eventService.getAllEventsSubscribed(user);

        for(Event eventoPrinc : listaEventos){
            for(Event eventoSubs : listaEventosSubscribed){
                if(eventoPrinc.getId().equals(eventoSubs.getId())){
                    eventoSubs.setSubscribed(true);
                }
            }
        }
       /*
        listaEventos.stream()
                .filter(evento -> listaEventosSubscribed.stream()
                .anyMatch(subscribedEvent -> subscribedEvent.getId() == evento.getId()))
                .forEach(evento -> evento.setSubscribed(true));
*/
        return ResponseEntity.ok(listaEventos);
    }

    @PostMapping
    public ResponseEntity addEvent(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") String date,
            @RequestParam("location") String location,
            @RequestParam("transport") Boolean transport,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            // Salvar os outros dados do evento no banco de dados
            Event event = new Event();
            event.setName(name);
            event.setDescription(description);
            event.setDate(LocalDateTime.parse(date));
            event.setLocation(location);
            event.setTransport(transport);
            event.setCreatedAt(LocalDateTime.now());
            event.setUpdatedAt(LocalDateTime.now());

            if (image != null && !image.isEmpty()) {

                // Gerar nome aleatorio para imagem
                String imageFileName = UUID.randomUUID().toString() + ".jpg";

                File file = new File(uploadDir + "/" + imageFileName);
                if (!file.exists()) {
                    file.mkdirs(); // criar diretoria se nao existir
                }
                image.transferTo(file);

                event.setImage(imageFileName);
            } else {
                event.setImage("default_image.jpg");
            }

            eventService.addEvent(event);

            return ResponseEntity.ok().body(new DefaultResponseDTO(200, "Evento criado com sucesso"));
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Imagem demasiado grande!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
        }
    }

    @PutMapping
    public ResponseEntity editEvent(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") String date,
            @RequestParam("location") String location,
            @RequestParam("transport") Boolean transport,
            @RequestParam("createdAt") String createdAt,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("imageFileName") String imageFileName) {

        // Salvar os outros dados do evento no banco de dados
        Optional<Event> event = eventService.getEvent(id);

        if (event.isPresent()) {
            try {
                Event eventToEdit = event.get();

                if (!name.isEmpty()) {
                    eventToEdit.setName(name);
                }
                
                if (!description.isEmpty()) {
                    eventToEdit.setDescription(description);
                }
                
                if (!date.isEmpty()) {
                    eventToEdit.setDate(LocalDateTime.parse(date));
                }
                
                if (!location.isEmpty()) {
                    eventToEdit.setLocation(location);
                }
                
                eventToEdit.setTransport(transport);
                
                eventToEdit.setUpdatedAt(LocalDateTime.now());
                

                if (image != null && !image.isEmpty()) {

                    // Gerar nome aleatorio para imagem
                    String imageFileNameFinal = UUID.randomUUID().toString() + ".jpg";

                    File file = new File(uploadDir + "/" + imageFileNameFinal);
                    if (!file.exists()) {
                        file.mkdirs(); // criar diretoria se nao existir
                    }
                    image.transferTo(file);

                    eventToEdit.setImage(imageFileNameFinal);
                }

                eventService.addEvent(eventToEdit);

                return ResponseEntity.ok().body(new DefaultResponseDTO(200, "Evento editado com sucesso"));
            } catch (MaxUploadSizeExceededException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Imagem demasiado grande!");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
            }
        } else {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable int id) {
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    @PostMapping("subscribe")
    public ResponseEntity susbcribeEvent(@RequestBody SubscribeEventDTO subscribeEventDTO) {

        LocalDate localDate = LocalDate.now();
        EventParticipant eventParticipant = new EventParticipant();

        Optional<Event> event = eventService.getEvent(subscribeEventDTO.eventID());
        if (event.isPresent()) {
            eventParticipant.setEvent(event.get());
        } else {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_EVENT;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        User user = userService.getUserByEmail(subscribeEventDTO.email());
        if (user != null) {
            eventParticipant.setUser(user);
        } else {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        eventParticipant.setCreatedAt(localDate);
        eventParticipant.setUpdatedAt(localDate);
        eventParticipant.setParticipationDate(localDate);
        eventParticipant.setCanceled(false);

        eventService.subscribeEvent(eventParticipant);

        return ResponseEntity.ok().build();
    }

    @PostMapping("cancel")
    public ResponseEntity cancelSubscription(@RequestBody SubscribeEventDTO subscribeEventDTO) {

        Optional<Event> event = eventService.getEvent(subscribeEventDTO.eventID());
        if (!event.isPresent()) {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_EVENT;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        User user = userService.getUserByEmail(subscribeEventDTO.email());
        if (user == null) {
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        if (eventService.cancelSubscription(event.get(), user)) {
            return ResponseEntity.ok().build();
        }

        ErrorsEnum error = ErrorsEnum.ERROR_CANCELING_SUBSCRIPTION;
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        // Construa o caminho completo para a imagem
        Path imagePath = Paths.get(uploadDir).resolve(imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        // Verifique se a imagem existe
        if (resource.exists() && resource.isReadable()) {
            System.out.println("Image found. Loading it...");
            MediaType mediaType = MediaType.IMAGE_JPEG;
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } else {
            // Retorne uma resposta 404 se a imagem não for encontrada
            return ResponseEntity.notFound().build();
        }
    }

}
