package se.mk.active.sampledata;


import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import se.mk.active.model.File;

public final class FileData {
    private FileData() {}

    public static final String NAME = "file";
    public static final String FILE_NAME = "file.txt";
    public static final String FILE_TYPE = "text/plain";
    public static final String DATA = "File content";

    public static MockMultipartFile createMockFile() {
        return new MockMultipartFile(
                NAME,
                FILE_NAME,
                MediaType.APPLICATION_JSON_VALUE,
                DATA.getBytes());
    }

    public static File createFile() {
        return new File(1L, FILE_NAME, FILE_TYPE, new byte[] {}, VenueData.createVenue());
    }
}
