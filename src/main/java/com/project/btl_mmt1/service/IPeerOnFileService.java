package com.project.btl_mmt1.service;

import com.project.btl_mmt1.dto.AnnounceDTO;
import com.project.btl_mmt1.dto.UnlinkDTO;
import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.models.PeerRole;
import com.project.btl_mmt1.responses.AnnounceResponseDTO;
import com.project.btl_mmt1.responses.ScrapeDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPeerOnFileService {
    PeerOnFile create(File newFile, Peer existPeer, PeerRole peerRole);

    PeerOnFile update(Peer peer, File file, PeerRole role);

    void unlink(String address, int port, String infoHash);

    ResponseEntity<AnnounceResponseDTO> announce(AnnounceDTO dto);

    PeerOnFile createPOFByInfoHashAndPeerAddress(AnnounceDTO dto);

    ScrapeDTO scrape(String infoHash);
    
    ResponseEntity<?> unLink(UnlinkDTO dto);
}
