package se.mk.active.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.mk.active.model.Event;
import se.mk.active.model.Venue;
import se.mk.active.repository.EventRepository;
import se.mk.active.repository.VenueRepository;
import se.mk.active.service.exception.ResourceNotFoundException;

import java.util.Optional;

import static se.mk.active.service.exception.ErrorMsg.EVENT_NOT_FOUND;
import static se.mk.active.service.exception.ErrorMsg.createErrorMsgAndLog;

@Service
public final class EventServiceImpl implements EventService {

    private static final Logger LOG = LoggerFactory.getLogger(EventServiceImpl.class);

    private EventRepository eventRepository;

    private VenueRepository venueRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Event createEvent(Event event, Long venueId) {
        Optional<Venue> optVenue = venueRepository.findById(venueId);

        if(optVenue.isPresent()) {
            event.setVenue(optVenue.get());
            return eventRepository.save(event);
        } else {
            throw new ResourceNotFoundException(createErrorMsgAndLog(EVENT_NOT_FOUND, venueId, LOG));
        }
    }

    @Override
    public Event getEventById(Long id) {
        Optional<Event> optEvent = eventRepository.findById(id);

        if(optEvent.isPresent()) {
            return optEvent.get();
        } else {
            String errorMsg = String.format(EVENT_NOT_FOUND, id);
            LOG.error(errorMsg);
            throw new ResourceNotFoundException(createErrorMsgAndLog(EVENT_NOT_FOUND, id, LOG));
        }
    }

    @Override
    public void deleteById(Long id) {
        this.eventRepository.deleteById(id);
    }
}
