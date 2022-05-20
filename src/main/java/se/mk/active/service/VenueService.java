package se.mk.active.service;

import se.mk.active.model.Venue;

public interface VenueService {
    Venue createVenue(Venue venue, Long providerId);
    Venue getVenueById(Long id);
    void deleteById(Long id);
}
