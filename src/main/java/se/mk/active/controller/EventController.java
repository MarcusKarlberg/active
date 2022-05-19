package se.mk.active.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.mk.active.model.Event;
import se.mk.active.model.EventDto;
import se.mk.active.service.EventService;

@RestController
@RequestMapping("/api/v1/events")
public final class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createVenue(@RequestBody EventDto eventDto) {
        final Event event = eventDto.toEvent();
        return new ResponseEntity<>(eventService.createEvent(event, eventDto.getVenueId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }
}
