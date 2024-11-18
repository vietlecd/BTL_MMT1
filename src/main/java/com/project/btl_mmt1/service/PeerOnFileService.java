package com.project.btl_mmt1.service;

import com.project.btl_mmt1.entity.File;
import com.project.btl_mmt1.entity.Peer;
import com.project.btl_mmt1.entity.PeerOnFile;
import com.project.btl_mmt1.repositories.PeerOnFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeerOnFileService implements IPeerOnFileService{
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
