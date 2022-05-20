package se.mk.active.service;

import org.springframework.web.multipart.MultipartFile;
import se.mk.active.model.File;

import java.util.Collection;

public interface FileService {
    File storeVenueFile(MultipartFile file, Long venueId);
    File getFileById(Long id);
    Collection<File> getAllFilesByVenueId(Long venueId);
}
