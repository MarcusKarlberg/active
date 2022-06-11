package se.marcuskarlberg.event.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.marcuskarlberg.event.model.Event;
import se.marcuskarlberg.event.repository.EventRepository;

@Component
public class EventHandler {

    private EventRepository eventRepository;

    public EventHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Mono<ServerResponse> createEvent(ServerRequest request) {
        return request.bodyToMono(Event.class)
            .flatMap(eventRepository::save)
            .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
        }

    public Mono<ServerResponse> getEvents(ServerRequest request) {
        Flux<Event> events = eventRepository.findAll();
        return ServerResponse.ok().body(events, Event.class);
    }
}


