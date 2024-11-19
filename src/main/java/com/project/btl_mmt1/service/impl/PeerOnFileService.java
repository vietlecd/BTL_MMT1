package com.project.btl_mmt1.service.impl;

import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.repositories.PeerOnFileRepository;
import com.project.btl_mmt1.service.IPeerOnFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeerOnFileService implements IPeerOnFileService {
    @Autowired
    private PeerOnFileRepository peerOnFileRepository;
    @Override
    public PeerOnFile create(File newFile, Peer existPeer) {
        PeerOnFile peerOnFile = PeerOnFile.builder()
                .fileId(newFile)
                .peerId(existPeer)
                .build();

        return peerOnFileRepository.save(peerOnFile);
    }
}
