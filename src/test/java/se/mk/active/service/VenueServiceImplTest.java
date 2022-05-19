package se.mk.active.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.mk.active.model.Provider;
import se.mk.active.model.Venue;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.VenueRepository;
import se.mk.active.sampledata.ProviderData;
import se.mk.active.sampledata.VenueData;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VenueServiceImplTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ProviderRepository providerRepository;

    private VenueService venueService;

    private Venue venue;

    private Provider provider;

    @BeforeEach
    public void beforeEach() {
        this.venueService = new VenueServiceImpl(venueRepository, providerRepository);
        this.venue = VenueData.createVenue();
        this.provider = ProviderData.createProvider();
    }

    @Test
    void createVenueSuccessTest() {
        when(providerRepository.findById(any())).thenReturn(Optional.ofNullable(this.provider));
        when(venueRepository.save(any())).thenReturn(this.venue);

        Venue newVenue = venueService.createVenue(this.venue, 1L);
        assertEquals(newVenue, venue);
        verify(providerRepository, times(1)).findById(any());
        verify(venueRepository, times(1)).save(any());
    }

    @Test
    void getVenueByIdSuccessTest() {
        when(venueRepository.findById(any())).thenReturn(Optional.ofNullable(this.venue));

        Venue newVenue = venueService.getVenueById(1L);
        assertEquals(newVenue, this.venue);
        verify(venueRepository, times(1)).findById(any());
    }
}