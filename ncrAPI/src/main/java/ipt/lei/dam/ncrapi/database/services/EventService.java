package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.repos.EventRepository;
import ipt.lei.dam.ncrapi.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Boolean addEvent(Event event) {
        try {
            Event savedEvent = eventRepository.save(event);
            return true; 
        } catch (Exception e) {
            return false;
        }
    }
    
    public Boolean deleteEvent(int id){
        try {
            Optional<Event> event = eventRepository.findById(id);
            if(event != null){
                eventRepository.deleteById(id);
            }
            return true; 
        } catch (Exception e) {
            return false;
        }
    }

}
