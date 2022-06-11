package se.marcuskarlberg.event.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import se.marcuskarlberg.event.model.Event;

public interface EventRepository extends ReactiveMongoRepository<Event, String> {
}
