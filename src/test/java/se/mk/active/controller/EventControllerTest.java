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
import se.mk.active.model.Event;
import se.mk.active.sampledata.EventData;
import se.mk.active.service.EventService;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = EventController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {

    private static final String URL_EVENTS = "/api/v1/events";

    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private Event event;

    @BeforeEach
    public void beforeEach() {
        assertThat(mockMvc).isNotNull();
        assertThat(eventService).isNotNull();
        this.event = EventData.createEvent();
    }

    @Test
    void createVenueSuccessTest() throws Exception {
        when(eventService.createEvent(any(), any())).thenReturn(this.event);
        String json = mapper.writeValueAsString(this.event);

        mockMvc.perform(post(URL_EVENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(json))
                .andDo(print())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(EventData.NAME))
                .andExpect(status().isCreated());
    }

    @Test
    void getEventByIdSuccessTest() throws Exception {
        when(eventService.getEventById(any())).thenReturn(this.event);

        mockMvc.perform(get(URL_EVENTS.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(EventData.NAME))
                .andExpect(status().isOk());
    }
}