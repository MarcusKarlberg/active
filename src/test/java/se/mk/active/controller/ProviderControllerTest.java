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
import se.mk.active.model.Provider;
import se.mk.active.sampledata.ProviderData;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;
import se.mk.active.service.ProviderService;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProviderController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProviderControllerTest {

    //MockBeans for Spring Security
    @MockBean
    private JwtBuilderParserImpl jwtBuilderParser;

    @MockBean
    private SecurityConstants securityConstants;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder encoder;
    private static final String URL_PROVIDERS = "/api/v1/providers";
    private static ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ProviderService providerService;
    @Autowired
    private MockMvc mockMvc;

    private Provider provider;

    @BeforeEach
    public void beforeEach() {
        assertThat(mockMvc).isNotNull();
        assertThat(providerService).isNotNull();
        this.provider = ProviderData.createProvider();
    }

    @Test
    void createProviderSuccessTest() throws Exception {
        when(providerService.createProvider(any())).thenReturn(this.provider);
        String json = mapper.writeValueAsString(this.provider);

        mockMvc.perform(post(URL_PROVIDERS)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(json))
            .andDo(print())
                .andExpect(jsonPath("$.name").value(ProviderData.PROVIDER_NAME))
                .andExpect(jsonPath("$.contactInfo.streetAddress").value(ProviderData.STREET))
                .andExpect(jsonPath("$.contactInfo.email").value(ProviderData.EMAIL))
                .andExpect(jsonPath("$.contactInfo.town").value(ProviderData.TOWN))
                .andExpect(jsonPath("$.contactInfo.country").value(ProviderData.COUNTRY))
                .andExpect(jsonPath("$.contactInfo.phoneNumber").value(ProviderData.P_NUMBER))
                .andExpect(jsonPath("$.contactInfo.zipCode").value(ProviderData.ZIP))
                .andExpect(jsonPath("$.contactInfo.url").value(ProviderData.URL))
                .andExpect(status().isCreated());
    }

    @Test
    void getProviderByIdSuccessTest() throws Exception {
        when(providerService.getProviderById(any())).thenReturn(this.provider);

        mockMvc.perform(get(URL_PROVIDERS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(ProviderData.PROVIDER_NAME))
                .andExpect(jsonPath("$.contactInfo.streetAddress").value(ProviderData.STREET))
                .andExpect(jsonPath("$.contactInfo.email").value(ProviderData.EMAIL))
                .andExpect(jsonPath("$.contactInfo.town").value(ProviderData.TOWN))
                .andExpect(jsonPath("$.contactInfo.country").value(ProviderData.COUNTRY))
                .andExpect(jsonPath("$.contactInfo.phoneNumber").value(ProviderData.P_NUMBER))
                .andExpect(jsonPath("$.contactInfo.zipCode").value(ProviderData.ZIP))
                .andExpect(jsonPath("$.contactInfo.url").value(ProviderData.URL))
                .andExpect(status().isOk());
    }
}