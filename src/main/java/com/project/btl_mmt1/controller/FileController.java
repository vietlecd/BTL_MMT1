package com.project.btl_mmt1.controller;

import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.service.IFileService;
import com.project.btl_mmt1.dto.CreatePeerOnFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;
    @GetMapping
    public ResponseEntity<?> search(@RequestParam(required = false) String hashInfo) {
        try {
            List<File> files = fileService.search(hashInfo);
            return ResponseEntity.ok(files);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @PostMapping
    public ResponseEntity<?> upload(@RequestBody UploadFileDto uploadFileDto) {
        try {
            File file = fileService.create(uploadFileDto);
            return ResponseEntity.status(201).body(file);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("message", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Loi kh may xay ra"));
        }

    }
    @PostMapping("/peers/announce")
    public ResponseEntity<?> createPeerOnFile(@RequestBody CreatePeerOnFileDto createPeerOnFileDto) {
        try {
            PeerOnFile peerOnFile = fileService.createPOFByInfoHashAndPeerAddress(createPeerOnFileDto);
            return ResponseEntity.status(201).body(peerOnFile);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("message", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Loi kh may xay ra"));
        }

    }
}
