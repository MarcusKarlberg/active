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
import se.mk.active.model.Event;
import se.mk.active.model.Venue;
import se.mk.active.repository.EventRepository;
import se.mk.active.repository.VenueRepository;
import se.mk.active.sampledata.EventData;
import se.mk.active.sampledata.VenueData;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceImplTest {

    //MockBeans for Spring Security
    @MockBean
    private JwtBuilderParserImpl jwtBuilderParser;

    @MockBean
    private SecurityConstants securityConstants;

    @MockBean
    private UserController userController;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    private DataInitializer dataInitializer;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    private Event event;

    private Venue venue;

    private EventService eventService;

    @BeforeEach
    public void beforeEach() {
        this.eventService = new EventServiceImpl(eventRepository, venueRepository);
        this.event = EventData.createEvent();
        this.venue = VenueData.createVenue();
    }

    @Test
    void createEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(this.event);
        when(venueRepository.findById(any())).thenReturn(Optional.ofNullable(this.venue));

        Event newEvent = eventService.createEvent(this.event, 1L);
        assertEquals(newEvent, event);
        verify(eventRepository, times(1)).save(any());
        verify(venueRepository, times(1)).findById(any());
    }

    @Test
    void getEventById() {
        when(eventRepository.findById(any())).thenReturn(Optional.ofNullable(this.event));

        Event foundEvent = eventService.getEventById(1L);
        assertEquals(foundEvent, this.event);
        verify(eventRepository, times(1)).findById(any());
    }
}