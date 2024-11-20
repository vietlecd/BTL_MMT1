package com.project.btl_mmt1.service;

import com.project.btl_mmt1.models.Peer;

import java.util.List;
import java.util.Map;

public interface IPeerService {
    Peer create(String address, int port);

    Peer findByAddressAndPort(String address, int port);

    List<Map<String, Object>> getSeeders(String infoHash);

  //  boolean doesPeerContainFile(String peerAddress, int peerPort, String fileId);

}
