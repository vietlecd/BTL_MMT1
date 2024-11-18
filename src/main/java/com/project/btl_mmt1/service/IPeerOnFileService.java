package com.project.btl_mmt1.service;

import com.project.btl_mmt1.entity.File;
import com.project.btl_mmt1.entity.Peer;
import com.project.btl_mmt1.entity.PeerOnFile;

public interface IPeerOnFileService {
    PeerOnFile create(File newFile, Peer existPeer);

}
