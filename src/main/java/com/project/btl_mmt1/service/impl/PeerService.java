package com.project.btl_mmt1.service.impl;
import com.project.btl_mmt1.customexceptions.DataNotFoundException;
import com.project.btl_mmt1.dto.AnnounceDTO;
import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.models.PeerRole;
import com.project.btl_mmt1.repositories.FileRepository;
import com.project.btl_mmt1.repositories.PeerOnFileRepository;
import com.project.btl_mmt1.repositories.PeerRepository;
import com.project.btl_mmt1.service.IPeerOnFileService;
import com.project.btl_mmt1.service.IPeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeerService implements IPeerService {

    private final PeerRepository peerRepository;
    private final FileRepository fileRepository;
    private final PeerOnFileRepository peerOnFileRepository;

    @Override
    public Peer create(String address, int port) {
        Peer peer = new Peer();
        peer.setAddress(address);
        peer.setPort(port);
        return peerRepository.save(peer);
    }

    @Override
    public Peer findByAddressAndPort(String address, int port) {
        Optional<Peer> peerOpt = peerRepository.findByAddressAndPort(address, port);
        if (peerOpt.isPresent()) {
            return peerOpt.get();
        }
        return create(address, port);
    }

//    @Override
//    public boolean doesPeerContainFile(String peerAddress, int peerPort, String fileId) {
//        return peerRepository.existsByAddressAndPortAndFilesId(peerAddress, peerPort, fileId);
//    }

    @Override
    public List<Map<String, Object>> getSeeders(String infoHash) {

        File existedFile = fileRepository.findByHashInfo(infoHash)
                .orElseThrow(() -> new DataNotFoundException("File không tồn tại"));

        List<PeerOnFile> seedersOnFile = peerOnFileRepository.findAllByFileIdAndPeerRole(existedFile, PeerRole.SEEDER);

//        return seedersOnFile.stream()
//                .flatMap(newPear -> PeerOnFile.getPeerId().getFiles()).stream()
//                .map(PeerOnFile::getPeerId)
//                .collect(Collectors.toList());
        return seedersOnFile.stream()
                .map(peerOnFile -> {
                    Peer peer = peerOnFile.getPeerId();
                    List<String> fileIds = peer.getFiles().stream()
                            .map(File::getHashInfo)
                            .collect(Collectors.toList());

                    // Tạo Map chứa Peer info và fileIds
                    return Map.of(
                            "id", peer.getId(),
                            "address", peer.getAddress(),
                            "port", peer.getPort(),
                            "files_hashinfo", fileIds
                    );
                })
                .collect(Collectors.toList());
    }
}

