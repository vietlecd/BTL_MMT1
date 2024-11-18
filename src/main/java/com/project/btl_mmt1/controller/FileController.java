package com.project.btl_mmt1.controller;

import com.project.btl_mmt1.dto.CreatePeerOnFileDto;
import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.entity.File;
import com.project.btl_mmt1.entity.PeerOnFile;
import com.project.btl_mmt1.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;
    @GetMapping
    public ResponseEntity<List<File>> search(@RequestParam(required = false) String hashInfo) {
        List<File> files = fileService.search(hashInfo);
        return ResponseEntity.ok(files);
    }
    @PostMapping("/create")
    public ResponseEntity<File> create(@RequestBody UploadFileDto uploadFileDto) {
        File file = fileService.create(uploadFileDto);
        return ResponseEntity.status(201).body(file);
    }
    @PostMapping("/peer-on-file")
    public ResponseEntity<PeerOnFile> createPeerOnFile(@RequestBody CreatePeerOnFileDto createPeerOnFileDto) {
        PeerOnFile peerOnFile = fileService.createPOFByInfoHashAndPeerAddress(createPeerOnFileDto);
        return ResponseEntity.status(201).body(peerOnFile);
    }
}
