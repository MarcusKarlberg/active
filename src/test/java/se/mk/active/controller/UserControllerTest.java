package se.mk.active.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.mk.active.model.User;
import se.mk.active.sampledata.UserData;
import se.mk.active.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    private static final String URL_USERS = "/api/v1/users";

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User basicUser;

    @BeforeEach
    public void setup() {
        assertThat(mockMvc).isNotNull();
        assertThat(userService).isNotNull();
        basicUser = UserData.createUser();
    }

    @Test
    public void createUserTestSuccess() throws Exception {
        when(userService.createUser(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(this.basicUser);
        String json = mapper.writeValueAsString(this.basicUser);

        mockMvc.perform(post(URL_USERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .characterEncoding(StandardCharsets.UTF_8)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
                .andExpect(jsonPath("$.username").value(UserData.USERNAME))
                .andExpect(jsonPath("$.password").value(UserData.PASSWORD))
                .andExpect(jsonPath("$.role").value(UserData.ROLE.name()))
                .andExpect(status().isCreated());
    }

    @Test
    public void getUserByIdTestSuccess() throws Exception {
        when(userService.getUser(any())).thenReturn(this.basicUser);

        mockMvc.perform(get(URL_USERS.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(UserData.ID.toString()))
                .andExpect(jsonPath("$.username").value(UserData.USERNAME))
                .andExpect(jsonPath("$.password").value(UserData.PASSWORD))
                .andExpect(jsonPath("$.role").value(UserData.ROLE.name()))
                .andExpect(status().isOk());
    }
}