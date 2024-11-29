package com.project.btl_mmt1.controller;

import com.project.btl_mmt1.dto.UnlinkDTO;
import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.responses.AnnounceResponseDTO;
import com.project.btl_mmt1.responses.FetchResponseDTO;
import com.project.btl_mmt1.responses.FileResponseDto;
import com.project.btl_mmt1.service.IFileService;
import com.project.btl_mmt1.dto.AnnounceDTO;
import com.project.btl_mmt1.service.IPeerOnFileService;
import com.project.btl_mmt1.service.IPeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;
    private final IPeerOnFileService peerOnFileService;
    private final IPeerService peerService;

    @GetMapping("/fetch_all")
    public ResponseEntity<?> search() {
        try {
            List<?> files = fileService.find_all();
            return ResponseEntity.ok(files);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/fetch")
    public ResponseEntity<?> search(@RequestParam(required = false) String hashInfo) {
        try {
            FetchResponseDTO files = fileService.search(hashInfo);
            return ResponseEntity.ok(files);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @PostMapping("/publish")
    public ResponseEntity<?> upload(@RequestBody UploadFileDto uploadFileDto) {
        try {
            FileResponseDto file = fileService.create(uploadFileDto);

            return ResponseEntity.status(201).body(file);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("message", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Loi kh may xay ra. Vui long th lai"));
        }

    }
    @PostMapping("/peers/announce")
    public ResponseEntity<?> createPeerOnFile(@RequestBody AnnounceDTO announceDTO) {
        try {
            AnnounceResponseDTO peerOnFile = peerOnFileService.announce(announceDTO).getBody();
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

    @GetMapping("/get/seeders")
    public ResponseEntity<?> getSeeders(@RequestParam String infoHash) {
        return ResponseEntity.ok(peerService.getSeeders(infoHash));
    }

    @PostMapping("/unlink")
    public ResponseEntity<?> unLink(@RequestBody UnlinkDTO dto) {
        return ResponseEntity.ok(peerOnFileService.unLink(dto));
    }
}
