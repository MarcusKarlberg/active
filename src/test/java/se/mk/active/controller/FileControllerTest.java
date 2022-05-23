package se.mk.active.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import se.mk.active.model.File;
import se.mk.active.sampledata.FileData;
import se.mk.active.sampledata.VenueData;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;
import se.mk.active.service.FileService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = FileController.class)
@AutoConfigureMockMvc(addFilters = false)
class FileControllerTest {

    //MockBeans for Spring Security
    @MockBean
    private JwtBuilderParserImpl jwtBuilderParser;

    @MockBean
    private SecurityConstants securityConstants;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder encoder;
    private static final String URL_UPLOAD = "/api/v1/files/upload/venue/1";

    private static final String URL_DOWNLOAD = "/api/v1/files/download/4";

    private static final String URL_DOWNLOAD_ALL = "/api/v1/files/download/venue/3";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    private MockMultipartFile mockFile;

    private File file;

    @BeforeEach
    public void beforeEach() {
        assertThat(mockMvc).isNotNull();
        assertThat(fileService).isNotNull();
        mockFile = FileData.createMockFile();
        file = FileData.createFile();
    }

    @Test
    void uploadFileToVenue() throws Exception {
        when(fileService.storeVenueFile(any(MultipartFile.class), any(Long.class))).thenReturn(file);
        mockMvc.perform(multipart(URL_UPLOAD).file(mockFile)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(FileData.FILE_NAME))
                .andExpect(jsonPath("$.type").value(FileData.FILE_TYPE))
                .andExpect(status().isCreated());
    }

    @Test
    void downloadVenueFileById() throws Exception {
        when(fileService.getFileById(any())).thenReturn(file);

        mockMvc.perform(get(URL_DOWNLOAD)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(FileData.FILE_NAME))
                .andExpect(jsonPath("$.type").value(FileData.FILE_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    void downloadAllFilesByVenueId() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(file);
        when(fileService.getAllFilesByVenueId(any())).thenReturn(files);

        mockMvc.perform(get(URL_DOWNLOAD_ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].name").value(FileData.FILE_NAME))
                .andExpect(jsonPath("$.[0].type").value(FileData.FILE_TYPE))
                .andExpect(status().isOk());
    }
}