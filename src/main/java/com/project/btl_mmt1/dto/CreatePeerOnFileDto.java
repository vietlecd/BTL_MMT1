package com.project.btl_mmt1.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePeerOnFileDto {
    private String infoHash;
    private String fileName;
    private long fileSize;
    private String peerAddress;
    private int peerPort;
}

