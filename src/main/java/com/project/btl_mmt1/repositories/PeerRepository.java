package com.project.btl_mmt1.repositories;

import com.project.btl_mmt1.models.Peer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PeerRepository extends MongoRepository<Peer, String> {

    Optional<Peer> findByAddressAndPort(String address, int port);
}
