package se.mk.active.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mk.active.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
