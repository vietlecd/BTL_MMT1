package com.project.btl_mmt1.service.impl;
import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.models.*;
import com.project.btl_mmt1.repositories.FileRepository;
import com.project.btl_mmt1.repositories.PeerOnFileRepository;
import com.project.btl_mmt1.repositories.PeerRepository;
import com.project.btl_mmt1.responses.FetchResponseDTO;
import com.project.btl_mmt1.responses.FileResponseDto;
import com.project.btl_mmt1.service.IFileService;
import com.project.btl_mmt1.service.IPeerOnFileService;
import com.project.btl_mmt1.service.IPeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<?> find_all() {
        List<File> fileList = fileRepository.findAll();

        List<FileResponseDto> responseDtos = new ArrayList<>();

        for (File file : fileList) {
            FileResponseDto dto = FileResponseDto.builder()
                    .hashInfo(file.getHashInfo())
                    .size(file.getSize())
                    .fileName(file.getName())
                    .build();

            responseDtos.add(dto);
        }

        return responseDtos;
    }

    @Override
    public FetchResponseDTO search(String hashInfo) {
        List<FetchResponseDTO> responseList = new ArrayList<>();

        Optional<File> fileOptional =  fileRepository.findByHashInfo(hashInfo);
        if (fileOptional.isEmpty()) {
            throw new RuntimeException("Khong co file nay");
        }

        File file = fileOptional.get();

        List<Peer> peerList = file.getPeers();

        List<Map<String, Object>> peers = new ArrayList<>();
        for (Peer peer : peerList) {
            Map<String, Object> maps = new HashMap<>();
            maps.put("address: ", peer.getAddress());
            maps.put("port: ", peer.getPort());
            peers.add(maps);
        }


        FetchResponseDTO res = FetchResponseDTO.builder()
                .fileName(file.getName())
                .fileSize(file.getSize())
                .peers(peers)
                .build();

        return res;
    }

    @Override
    public FileResponseDto create(UploadFileDto dto) {
        Optional<File> fileOps = fileRepository.findByHashInfo(dto.getHashInfo());
        File file = fileOps.orElseGet(() -> {
            File newFile = File.builder()
                    .hashInfo(dto.getHashInfo())
                    .name(dto.getName())
                    .size(dto.getSize())
                    .build();
            return fileRepository.save(newFile); // Lưu vào DB
        });

        Peer existedPeer = peerService.findByAddressAndPort(dto.getPeerAddress(), dto.getPeerPort());
        if (existedPeer.getFiles() != null) {
            boolean fileExists = existedPeer.getFiles().stream()
                    .anyMatch(f -> f.getId().equals(file.getId()));
            if (fileExists) {
                throw new RuntimeException("File này đã tồn tại trong danh sách của Peer");
            }
        } else {
            existedPeer.setFiles(new ArrayList<>());
        }

        existedPeer.getFiles().add(file);
        peerRepository.save(existedPeer); // Cập nhật Peer

        PeerOnFile peerOnFile = peerOnFileRepository.findByFileIdAndPeerId(file, existedPeer);

        if (peerOnFile == null) {
            peerOnFile = peerOnFileService.create(file, existedPeer, PeerRole.SEEDER);
//            throw new RuntimeException("Da ton tai roi");
            return null;
        }


        return FileResponseDto.builder()
                .fileName(file.getName())
                .hashInfo(file.getHashInfo())
                .size(file.getSize())
                .build();
    }

}
