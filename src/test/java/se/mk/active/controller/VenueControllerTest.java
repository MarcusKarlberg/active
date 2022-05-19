package se.mk.active.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.mk.active.model.Venue;
import se.mk.active.sampledata.VenueData;
import se.mk.active.service.VenueService;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VenueController.class)
@AutoConfigureMockMvc(addFilters = false)
class VenueControllerTest {

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
    }

    @Test
    void createVenueSuccessTest() throws Exception {
        when(venueService.createVenue(any(), any())).thenReturn(this.venue);
        String json = mapper.writeValueAsString(this.venue);

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
}