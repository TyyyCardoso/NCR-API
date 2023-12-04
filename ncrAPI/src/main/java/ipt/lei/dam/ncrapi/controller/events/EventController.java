package ipt.lei.dam.ncrapi.controller.events;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.services.EventService;
import ipt.lei.dam.ncrapi.dto.EventDTO;
import java.time.LocalDateTime;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public ResponseEntity events(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
    @PostMapping
    public ResponseEntity addEvent(@RequestBody EventDTO eventDTO){
        Event event = new Event();
        event.setName(eventDTO.name());
        event.setDescription(eventDTO.description());
        event.setDate(LocalDateTime.parse(eventDTO.date()));
        event.setLocation(eventDTO.location());
        event.setTransport(eventDTO.transport());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        event.setImage(eventDTO.image());
    
        return ResponseEntity.ok(eventService.addEvent(event));
    }

}
