package se.mk.active.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.mk.active.model.Venue;
import se.mk.active.model.VenueDto;
import se.mk.active.service.VenueService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {

    private VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(@Valid @RequestBody VenueDto venueDto) {
        final Venue venue = venueDto.toVenue();
        return new ResponseEntity<>(venueService.createVenue(venue, venueDto.getProviderId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Cacheable("venue-cache")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        return new ResponseEntity<>(venueService.getVenueById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CacheEvict("venue-cache")
    public ResponseEntity<Venue> deleteVenueById(@PathVariable Long id) {
        venueService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
