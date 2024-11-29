package com.project.btl_mmt1.service.impl;

import com.project.btl_mmt1.customexceptions.DataNotFoundException;
import com.project.btl_mmt1.dto.AnnounceDTO;
import com.project.btl_mmt1.dto.UnlinkDTO;
import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.models.PeerRole;
import com.project.btl_mmt1.repositories.FileRepository;
import com.project.btl_mmt1.repositories.PeerOnFileRepository;
import com.project.btl_mmt1.repositories.PeerRepository;
import com.project.btl_mmt1.responses.AnnounceResponseDTO;
import com.project.btl_mmt1.responses.ScrapeDTO;
import com.project.btl_mmt1.service.IPeerOnFileService;
import com.project.btl_mmt1.service.IPeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeerOnFileService implements IPeerOnFileService {

    private final PeerOnFileRepository peerOnFileRepository;
    private final PeerRepository peerRepository;
    private final FileRepository fileRepository;
    private final IPeerService peerService;

    @Override
    public PeerOnFile create(File newFile, Peer existPeer, PeerRole peerRole) {
        PeerOnFile peerOnFile = PeerOnFile.builder()
                .fileId(newFile)
                .peerId(existPeer)
                .peerRole(peerRole)
                .build();

        return peerOnFileRepository.save(peerOnFile);
    }

    @Override
    public PeerOnFile update(Peer peer, File file, PeerRole role) {
        PeerOnFile peerOnFile = peerOnFileRepository.findByFileIdAndPeerId(file, peer);
        if (peerOnFile == null) {
            return create(file, peer, role);
        }
        peerOnFile.setPeerRole(role);

        return peerOnFileRepository.save(peerOnFile);
    }

    @Override
    public void unlink(String address, int port, String infoHash) {
        Peer existedPeer = peerRepository.findByAddressAndPort(address, port)
                .orElseThrow(() -> new DataNotFoundException("Peer không tồn tại"));

        File existedFile = fileRepository.findByHashInfo(infoHash)
                .orElseThrow(() -> new DataNotFoundException("File không tồn tại"));

        peerOnFileRepository.deleteByPeerIdAndFileId(existedPeer, existedFile);
    }

    @Override
    public ResponseEntity<AnnounceResponseDTO> announce(AnnounceDTO dto) {

        File existedFile = fileRepository.findByHashInfo(dto.getInfoHash())
                .orElseThrow(() -> new DataNotFoundException("File không tồn tại"));
        System.out.print(existedFile);

        if (dto.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        System.out.print(dto.getStatus());
        if ("START".equals(dto.getStatus().name())) {
            Optional<Peer> existedPeer = Optional.ofNullable(peerService.findByAddressAndPort(dto.getPeerAddress(), dto.getPeerPort()));
            Peer peer;
            if (existedPeer.isEmpty()) {
                peer = peerService.create(dto.getPeerAddress(), dto.getPeerPort());
            } else {
                peer = existedPeer.get();
            }

            PeerOnFile peerOnFile = update(peer, existedFile, PeerRole.LEECHER);

            AnnounceResponseDTO res = AnnounceResponseDTO.builder()
                    .file_name(existedFile.getName())
                    .size(existedFile.getName())
                    .address(existedPeer.get().getAddress())
                    .build();

            return ResponseEntity.ok(res);
        }

        if ("COMPLETED".equals(dto.getStatus().name())) {
            Optional<Peer> existedPeer = Optional.ofNullable(peerService.findByAddressAndPort(dto.getPeerAddress(), dto.getPeerPort()));
            Peer peer;
            if (existedPeer.isEmpty()) {
                peer = peerService.create(dto.getPeerAddress(), dto.getPeerPort());
            } else {
                peer = existedPeer.get();
            }

            PeerOnFile peerOnFile = update(peer, existedFile, PeerRole.SEEDER);

            AnnounceResponseDTO res = AnnounceResponseDTO.builder()
                    .file_name(existedFile.getName())
                    .size(existedFile.getName())
                    .address(existedPeer.get().getAddress())
                    .build();
            return ResponseEntity.ok(res);
        }

        throw new IllegalArgumentException("Invalid status: " + dto.getStatus());
    }

    @Override
    public PeerOnFile createPOFByInfoHashAndPeerAddress(AnnounceDTO dto) {
        try {
            Peer existedPeer = peerService.findByAddressAndPort(dto.getPeerAddress(), dto.getPeerPort());
            if (existedPeer == null) {
                existedPeer = peerService.create(dto.getPeerAddress(), dto.getPeerPort());
            }

            File file = fileRepository.findByHashInfo(dto.getInfoHash()).orElseGet(() -> {
                File newFile = new File();
                newFile.setHashInfo(dto.getInfoHash());


                return fileRepository.save(newFile);
            });

            PeerOnFile peerOnFile = peerOnFileRepository.findByFileIdAndPeerId(file, existedPeer);
            if (peerOnFile != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Announcing failed since your upload has been recorded before");
            }


            if (existedPeer.getFiles() == null) {
                existedPeer.setFiles(new ArrayList<>());
            }
            existedPeer.getFiles().add(file);
            peerRepository.save(existedPeer);

            peerOnFile = create(file, existedPeer, PeerRole.SEEDER);

            return peerOnFile;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @Override
    public ScrapeDTO scrape(String infoHash) {
        // Tìm file dựa trên infoHash
        Optional<File> existedFile = fileRepository.findByHashInfo(infoHash);
        if (existedFile.isEmpty()) {
            throw new DataNotFoundException("Khong tim thay file");
        }

        File file = existedFile.get();

        PeerOnFile peersOnFile = peerOnFileRepository.findByFileId(file);

        ScrapeDTO scrapeDTO = ScrapeDTO.builder()
                .fileId(peersOnFile.getFileId().getId())
                .peerId(peersOnFile.getPeerId().getId())
                .peerRole(peersOnFile.getPeerRole())
                .build();
        return scrapeDTO;

    }

    @Override
    public ResponseEntity<?> unLink(UnlinkDTO dto) {
        Optional<Peer> peerOptional = peerRepository.findByAddressAndPort(dto.getAddress(), dto.getPort());
        if (peerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong the tim tha data Peer");
        }
        Peer peer = peerOptional.get();

        Optional<File> fileOptional = fileRepository.findByHashInfo(dto.getInfoHash());
        if (fileOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong the tim tha data Peer");
        }
        File file = fileOptional.get();

        peerOnFileRepository.deleteByPeerIdAndFileId(peer, file);
        return ResponseEntity.ok("Xoa thanh cong link giua peer va file");
    }


}
