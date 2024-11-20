package com.project.btl_mmt1.repositories;

import com.project.btl_mmt1.models.Peer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeerRepository extends JpaRepository<Peer, Long> {

    Optional<Peer> findByAddressAndPort(String address, int port);
//
//    @Query("{ 'address': ?0, 'port': ?1, 'files': ?2 }")
//    boolean existsByAddressAndPortAndFiles(String address, int port, File fileId);
}
