package ipt.lei.dam.ncrapi.controller.events;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.entities.EventParticipant;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.services.EventService;
import ipt.lei.dam.ncrapi.database.services.UserService;
import ipt.lei.dam.ncrapi.dto.ErrorResponseDTO;
import ipt.lei.dam.ncrapi.dto.EventDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ipt.lei.dam.ncrapi.dto.EventsDTO;
import ipt.lei.dam.ncrapi.dto.subscribe.SubscribeEventDTO;
import ipt.lei.dam.ncrapi.utils.enums.ErrorsEnum;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @PostMapping("/all")
    public ResponseEntity events(@RequestBody EventsDTO eventsDTO){

        if(eventsDTO.email().isEmpty()){
            return ResponseEntity.ok(eventService.getAllEvents());
        }

        List<Event> listaEventos = eventService.getAllEvents();

        User user = userService.getUserByEmail(eventsDTO.email());

        if(user==null){
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        for(Event evento : listaEventos){
            evento.setSubscribed(false);
            if(eventService.checkIfSubscribed(evento, user)){
                evento.setSubscribed(true);
            }
        }

        return ResponseEntity.ok(listaEventos);
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
    
    @PutMapping
    public ResponseEntity editEvent(@RequestBody EventDTO eventDTO){
        Event event = new Event();
        event.setId(eventDTO.id());
        event.setName(eventDTO.name());
        event.setDescription(eventDTO.description());
        event.setDate(LocalDateTime.parse(eventDTO.date()));
        event.setLocation(eventDTO.location());
        event.setTransport(eventDTO.transport());
        event.setCreatedAt(LocalDateTime.parse(eventDTO.createdAt()));
        event.setUpdatedAt(LocalDateTime.now());
        event.setImage(eventDTO.image());
        
        return ResponseEntity.ok(eventService.addEvent(event));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable int id){
         return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    @PostMapping("subscribe")
    public ResponseEntity susbcribeEvent(@RequestBody SubscribeEventDTO subscribeEventDTO) {

        LocalDate localDate = LocalDate.now();
        EventParticipant eventParticipant = new EventParticipant();

        Optional<Event> event = eventService.getEvent(subscribeEventDTO.eventID());
        if(event.isPresent()){
            eventParticipant.setEvent(event.get());
        }else{
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_EVENT;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        User user = userService.getUserByEmail(subscribeEventDTO.email());
        if(user!=null){
            eventParticipant.setUser(user);
        }else{
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
        if(!event.isPresent()){
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_EVENT;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        User user = userService.getUserByEmail(subscribeEventDTO.email());
        if(user==null){
            ErrorsEnum error = ErrorsEnum.ERROR_GETTING_USER;
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
        }

        if(eventService.cancelSubscription(event.get(), user)){
            return ResponseEntity.ok().build();
        }

        ErrorsEnum error = ErrorsEnum.ERROR_CANCELING_SUBSCRIPTION;
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(error.getErrorCode(), error.getMessage()));
    }


}
