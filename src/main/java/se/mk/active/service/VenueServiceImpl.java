package se.mk.active.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.mk.active.model.Provider;
import se.mk.active.model.Venue;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.VenueRepository;
import se.mk.active.service.exception.ResourceNotFoundException;

import java.util.Optional;

import static se.mk.active.service.exception.ErrorMsg.*;

@Service
public final class VenueServiceImpl implements VenueService {

    private static final Logger LOG = LoggerFactory.getLogger(VenueServiceImpl.class);
    private VenueRepository venueRepository;
    private ProviderRepository providerRepository;

    @Autowired
    public VenueServiceImpl(VenueRepository venueRepository, ProviderRepository providerRepository) {
        this.venueRepository = venueRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public Venue createVenue(Venue venue, Long providerId) {
        Optional<Provider> optProvider = providerRepository.findById(providerId);

        if(optProvider.isPresent()) {
            venue.setProvider(optProvider.get());
            return venueRepository.save(venue);
        } else {
            throw new ResourceNotFoundException(createErrorMsgAndLog(PROVIDER_NOT_FOUND, providerId, LOG));
        }
    }

    @Override
    public Venue getVenueById(Long id) {
        Optional<Venue> optVenue = venueRepository.findById(id);

        if(optVenue.isPresent()) {
            return optVenue.get();
        } else {
            throw new ResourceNotFoundException(createErrorMsgAndLog(VENUE_NOT_FOUND, id, LOG));
        }
    }

    @Override
    public void deleteById(Long id) {
        this.venueRepository.deleteById(id);
    }
}
