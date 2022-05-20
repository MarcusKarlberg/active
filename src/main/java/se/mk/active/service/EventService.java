package se.mk.active.service;

import se.mk.active.model.Event;

public interface EventService {
    Event createEvent(Event event, Long venueId);
    Event getEventById(Long id);
    void deleteById(Long id);
}
