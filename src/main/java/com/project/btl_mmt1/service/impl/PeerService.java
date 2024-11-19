package com.project.btl_mmt1.service.impl;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.repositories.PeerRepository;
import com.project.btl_mmt1.service.IPeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeerService implements IPeerService {
    @Autowired
    private PeerRepository peerRepository;

    @Override
    public Peer create(String address, int port) {
        Peer peer = new Peer();
        peer.setAddress(address);
        peer.setPort(port);
        return peerRepository.save(peer);
    }

    @Override
    public Peer findByAddressAndPort(String address, int port) {
        return peerRepository.findByAddressAndPort(address, port)
                .orElseGet(() -> create(address, port));
    }
}

