package se.mk.active.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mk.active.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
