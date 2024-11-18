package com.project.btl_mmt1.repositories;

import com.project.btl_mmt1.entity.PeerOnFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeerOnFileRepository extends MongoRepository<PeerOnFile, String> {
    PeerOnFile findByFileIdAndPeerId(String fileId, String peerId);
}
