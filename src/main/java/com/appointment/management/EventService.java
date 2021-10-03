package com.appointment.management;

import com.appointment.management.data.Event;
import com.appointment.management.data.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Optional<Event> saveEvent(Event event, String username) {
        var oCreator = userRepository.findOneByUsername(username);
        if (oCreator.isEmpty()) return Optional.empty();
        var creator = oCreator.get();
        for (Event eventCheck: creator.getEvents()) {
            if (isOverlapping(event, eventCheck)) {
                throw new IllegalArgumentException("Event is overlapping with event " + eventCheck.getTitle());
            }
        }
        event.setCreator(username);
        creator.getEvents().add(event);
        eventRepository.save(event);
        userRepository.save(creator);
        return Optional.of(event);
    }

    public Optional<Event> editEvent(Event eventEdits, String id, String username) {
        var oEvent = eventRepository.findById(Long.parseLong(id));
        if (oEvent.isEmpty()) throw new IllegalArgumentException("Event does not exist");
        var event = oEvent.get();

        var oCreator = userRepository.findOneByUsername(username);
        if (oCreator.isEmpty()) return Optional.empty();
        var creator = oCreator.get();
        for (Event eventCheck: creator.getEvents()) {
            if (isOverlapping(event, eventCheck)) {
                throw new IllegalArgumentException("Event is overlapping with event " + eventCheck.getTitle());
            }
        }

        event.setTitle(eventEdits.getTitle());
        event.setStartTime(eventEdits.getStartTime());
        event.setEndTime(eventEdits.getEndTime());
        eventRepository.save(event);
        return Optional.of(event);
    }

    public void deleteEvent(String id, String username) {
        var oEvent = eventRepository.findById(Long.parseLong(id));
        if (oEvent.isEmpty()) throw new IllegalArgumentException("Event does not exist");
        var event = oEvent.get();
        if (!event.getCreator().equals(username)) throw new IllegalArgumentException("You can only delete events you created");
        eventRepository.delete(event);
    }

    public List<Event> viewEventsByUser(String id) {
        var oUser = userRepository.findById(Long.parseLong(id));
        if (oUser.isEmpty()) throw new IllegalArgumentException("User does not exist");
        return oUser.get().getEvents();
    }

    public List<Event> viewEvents() {
        return eventRepository.findAll();
    }





    private boolean isOverlapping(Event event1, Event event2) {
        return event1.getStartTime().isBefore(event2.getEndTime()) && event2.getStartTime().isBefore(event1.getEndTime());
    }

}
