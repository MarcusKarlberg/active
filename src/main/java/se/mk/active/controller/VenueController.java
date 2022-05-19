package se.mk.active.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import se.mk.active.model.Venue;
import se.mk.active.model.VenueDto;
import se.mk.active.service.VenueService;

@RestController
@RequestMapping("/api/v1/venues")
public final class VenueController {

    private VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody VenueDto venueDto) {
        final Venue venue = venueDto.toVenue();
        return new ResponseEntity<>(venueService.createVenue(venue, venueDto.getProviderId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        return new ResponseEntity<>(venueService.getVenueById(id), HttpStatus.OK);
    }
}
