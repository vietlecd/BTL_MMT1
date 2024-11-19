package com.project.btl_mmt1.service;

import com.project.btl_mmt1.models.Peer;

public interface IPeerService {
    Peer create(String address, int port);

    Peer findByAddressAndPort(String address, int port);

}
