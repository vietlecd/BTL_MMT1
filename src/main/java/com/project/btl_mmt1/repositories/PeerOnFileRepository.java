package com.project.btl_mmt1.repositories;

import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.models.PeerRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeerOnFileRepository extends JpaRepository<PeerOnFile, Long> {
    PeerOnFile findByFileIdAndPeerId(File fileId, Peer peerId);


    PeerOnFile findByFileId(File fileId);

    List<PeerOnFile> findAllByFileIdAndPeerRole(File fileId, PeerRole peerRole);

    void deleteByPeerIdAndFileId(Peer peerId, File fileId);

//    void deleteByPeerPortAndPeerAddress(Peer peerPort, Peer peerAddress);
}
