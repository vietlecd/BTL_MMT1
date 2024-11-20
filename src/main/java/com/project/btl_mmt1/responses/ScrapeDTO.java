package com.project.btl_mmt1.responses;

import com.project.btl_mmt1.models.PeerRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ScrapeDTO {
    private Long fileId;
    private Long peerId;
    private PeerRole peerRole;
}
