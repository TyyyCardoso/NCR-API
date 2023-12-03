package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.repos.EventRepository;
import ipt.lei.dam.ncrapi.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
