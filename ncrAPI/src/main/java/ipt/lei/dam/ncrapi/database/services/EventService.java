package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.Event;
import ipt.lei.dam.ncrapi.database.entities.EventParticipant;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.repos.EventParticipantRepository;
import ipt.lei.dam.ncrapi.database.repos.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventParticipantRepository eventParticipantRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getAllEventsSubscribed(User user) {
        return eventRepository.findAllSubscribed(user);
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
            if(event.isPresent()){
                eventParticipantRepository.deleteAllByEventId(event.get());
                eventRepository.deleteById(id);
                return true; 
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Event> getEvent(int id){
        return eventRepository.findById(id);
    }

    public void subscribeEvent(EventParticipant eventParticipant){
        eventParticipantRepository.save(eventParticipant);
    }

    public boolean checkIfSubscribed(Event event, User user){
        EventParticipant eventParticipant = eventParticipantRepository.findSusbcribedEventByUser(event, user, false);

        return eventParticipant!=null;
    }

    public boolean cancelSubscription(Event event, User user){
        return eventParticipantRepository.cancelSubscription(event, user) > 0;
    }



}
