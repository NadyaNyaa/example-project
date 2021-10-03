package com.appointment.management;

import com.appointment.management.data.Event;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@Secured("ROLE_APPUSER")
public class EventEndpoint {
    private final EventService eventService;

    public EventEndpoint(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    Event saveEvent(@RequestBody Event event, Authentication authentication) {
        return eventService.saveEvent(event, authentication.getName()).orElse(null);
    }

    @PutMapping("/edit/{id}")
    Event editEvent(@RequestBody Event eventEdits, @PathVariable String id, Authentication authentication) {
        return eventService.editEvent(eventEdits, id, authentication.getName()).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    void deleteEvent(@PathVariable String id, Authentication authentication) {
        eventService.deleteEvent(id, authentication.getName());
    }

    @GetMapping("/view/{id}")
    List<Event> viewEventsByUser(@PathVariable String id) {
        return eventService.viewEventsByUser(id);
    }

    @GetMapping("/view")
    List<Event> viewEvents() {
        return eventService.viewEvents();
    }


}
