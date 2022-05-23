package se.mk.active.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.mk.active.controller.UserController;
import se.mk.active.init.DataInitializer;
import se.mk.active.model.Provider;
import se.mk.active.model.Venue;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.VenueRepository;
import se.mk.active.sampledata.ProviderData;
import se.mk.active.sampledata.VenueData;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VenueServiceImplTest {

    //MockBeans for Spring Security
    @MockBean
    private JwtBuilderParserImpl jwtBuilderParser;

    @MockBean
    private SecurityConstants securityConstants;

    @MockBean
    private UserController userController;

    @MockBean
    private DataInitializer dataInitializer;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder encoder;

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