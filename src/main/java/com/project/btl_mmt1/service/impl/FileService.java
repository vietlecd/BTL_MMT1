package com.project.btl_mmt1.service.impl;
import com.project.btl_mmt1.dto.CreatePeerOnFileDto;
import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.repositories.FileRepository;
import com.project.btl_mmt1.repositories.PeerOnFileRepository;
import com.project.btl_mmt1.repositories.PeerRepository;
import com.project.btl_mmt1.service.IFileService;
import com.project.btl_mmt1.service.IPeerOnFileService;
import com.project.btl_mmt1.service.IPeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;

@Service
@AllArgsConstructor
public class FileService implements IFileService {
    private FileRepository fileRepository;
    private IPeerService peerService;
    private PeerOnFileRepository peerOnFileRepository;
    private IPeerOnFileService peerOnFileService;
    private PeerRepository peerRepository;

    @Override
    public List<File> search(String hashInfo) {
        if (hashInfo != null && !hashInfo.isEmpty()) {
            return fileRepository.findByHashInfo(hashInfo)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        }
        return fileRepository.findAll();
    }

    @Override
    public File create(UploadFileDto dto) {
        Optional<File> fileOps = fileRepository.findByHashInfo(dto.getHashInfo());
        if (fileOps.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Giay already have");
        }

        File newFile = File.builder()
                .hashInfo(dto.getHashInfo())
                .name(dto.getName())
                .trackerUrl("http://localhost:8080/api")
                .size(dto.getSize())
                .build();
        fileRepository.save(newFile);

        Peer existedPeer = peerService.findByAddressAndPort(dto.getPeerAddress(), dto.getPeerPort());
        if (existedPeer == null ) {
            peerService.create(dto.getPeerAddress(), dto.getPeerPort());
        }

        assert existedPeer != null;

        if (existedPeer.getFiles() == null) {
            existedPeer.setFiles(new ArrayList<>());
        }
        existedPeer.getFiles().add(newFile);
        peerRepository.save(existedPeer);

        fileRepository.save(newFile);

        peerOnFileService.create(newFile, existedPeer);

        return newFile;
    }

    @Override
    public PeerOnFile createPOFByInfoHashAndPeerAddress(CreatePeerOnFileDto dto) {
        try {
            Peer existedPeer = peerService.findByAddressAndPort(dto.getPeerAddress(), dto.getPeerPort());
            if (existedPeer == null) {
                existedPeer = peerService.create(dto.getPeerAddress(), dto.getPeerPort());
            }

            File file = fileRepository.findByHashInfo(dto.getInfoHash()).orElseGet(() -> {
                File newFile = new File();
                newFile.setHashInfo(dto.getInfoHash());
                newFile.setTrackerUrl("http://localhost:8080/api");

                return fileRepository.save(newFile);
            });

            PeerOnFile peerOnFile = peerOnFileRepository.findByFileIdAndPeerId(file.getId(), existedPeer.getId());
            if (peerOnFile != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Announcing failed since your upload has been recorded before");
            }


            if (existedPeer.getFiles() == null) {
                existedPeer.setFiles(new ArrayList<>());
            }
            existedPeer.getFiles().add(file);
            peerRepository.save(existedPeer);

            peerOnFile = peerOnFileService.create(file, existedPeer);

            return peerOnFile;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

}
