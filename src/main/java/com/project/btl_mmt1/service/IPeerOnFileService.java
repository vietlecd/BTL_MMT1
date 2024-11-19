package com.project.btl_mmt1.service;

import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;

public interface IPeerOnFileService {
    PeerOnFile create(File newFile, Peer existPeer);

}
