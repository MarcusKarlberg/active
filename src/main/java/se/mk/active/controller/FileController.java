package se.mk.active.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.mk.active.model.File;
import se.mk.active.service.FileService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/venue/{id}")
    public ResponseEntity<File> uploadFileToVenue(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
       return new ResponseEntity<>(this.fileService.storeVenueFile(file, id), HttpStatus.CREATED);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<File> downloadVenueFileById(@PathVariable Long fileId) {
        return new ResponseEntity<>(this.fileService.getFileById(fileId), HttpStatus.OK);
    }

    @GetMapping("/download/venue/{venueId}")
    public ResponseEntity<Collection<File>> downloadAllFilesByVenueId(@PathVariable Long venueId) {
        return new ResponseEntity<>(this.fileService.getAllFilesByVenueId(venueId), HttpStatus.OK);
    }
}
