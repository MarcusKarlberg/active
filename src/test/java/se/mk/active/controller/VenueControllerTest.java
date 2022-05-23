package se.mk.active.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import se.mk.active.model.Venue;
import se.mk.active.model.VenueDto;
import se.mk.active.sampledata.ProviderData;
import se.mk.active.sampledata.VenueData;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;
import se.mk.active.service.VenueService;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VenueController.class)
@AutoConfigureMockMvc(addFilters = false)
class VenueControllerTest {

    //MockBeans for Spring Security
    @MockBean
    private JwtBuilderParserImpl jwtBuilderParser;

    @MockBean
    private SecurityConstants securityConstants;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder encoder;

    private static final String VENUE_API_URL = "/api/v1/venues";

    @MockBean
    private VenueService venueService;

    @Autowired
    private MockMvc mockMvc;

    private Venue venue;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        assertThat(mockMvc).isNotNull();
        assertThat(venueService).isNotNull();
        this.venue = VenueData.createVenue();
        this.venue.setProvider(ProviderData.createProvider());
    }

    @Test
    void createVenueSuccessTest() throws Exception {
        when(venueService.createVenue(any(), any())).thenReturn(this.venue);
        String json = mapper.writeValueAsString(VenueDto.toDto(this.venue));

        mockMvc.perform(post(VENUE_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(VenueData.VENUE_NAME))
                .andExpect(status().isCreated());
    }

    @Test
    void getVenueByIdSuccessTest() throws Exception {
        when(venueService.getVenueById(any())).thenReturn(this.venue);

        mockMvc.perform(get(VENUE_API_URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(VenueData.VENUE_NAME))
                .andExpect(status().isOk());
    }

    @Test
    void deleteVenueSuccessTest() throws Exception {
        mockMvc.perform(delete(VENUE_API_URL.concat("/1")))
                .andDo(print())
                .andExpect(status().isOk());
    }
}