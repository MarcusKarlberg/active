package se.mk.active.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import se.mk.active.model.File;
import se.mk.active.model.Venue;
import se.mk.active.repository.FileRepository;
import se.mk.active.repository.VenueRepository;
import se.mk.active.service.exception.ErrorMsg;
import se.mk.active.service.exception.FileException;
import se.mk.active.service.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static se.mk.active.service.exception.ErrorMsg.*;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
    private final FileRepository fileRepository;

    private final VenueRepository venueRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, VenueRepository venueRepository) {
        this.fileRepository = fileRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public File storeVenueFile(MultipartFile file, Long venueId) {
        Optional<Venue> optVenue = venueRepository.findById(venueId);

        if(optVenue.isPresent()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            try {
                File newFile = new File(fileName, file.getContentType(), file.getBytes());
                newFile.setVenue(optVenue.get());
                return fileRepository.save(newFile);
            } catch (IOException e) {
                String errorMsg = String.format("Failed to store file: %s - ", file.getName());
                throw new FileException(errorMsg.concat(e.getMessage()));
            }
        } else {
            String errorMsg = ErrorMsg.createErrorMsgAndLog(VENUE_NOT_FOUND, venueId, LOG);
            throw new ResourceNotFoundException(errorMsg);
        }
    }

    @Override
    public File getFileById(Long id) {
        Optional<File> optFile = fileRepository.findById(id);

        if(optFile.isPresent()) {
            return optFile.get();
        } else {
            String errorMsg = ErrorMsg.createErrorMsgAndLog(FILE_NOT_FOUND, id, LOG);
            throw new ResourceNotFoundException(errorMsg);
        }
    }

    @Override
    public Collection<File> getAllFilesByVenueId(Long venueId) {
        Optional<Venue> optVenue = venueRepository.findById(venueId);

        if(optVenue.isPresent()) {
            Venue venue = optVenue.get();
            if(venue.getFiles().isEmpty()) {
                String errorMsg = ErrorMsg.createErrorMsgAndLog(CONTAINS_NO_FILES, venueId, LOG);
                throw new ResourceNotFoundException(errorMsg);
            }
            return venue.getFiles();
        } else {
            String errorMsg = ErrorMsg.createErrorMsgAndLog(VENUE_NOT_FOUND, venueId, LOG);
            throw new ResourceNotFoundException(errorMsg);
        }
    }
}
