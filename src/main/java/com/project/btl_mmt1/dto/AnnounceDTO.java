package com.project.btl_mmt1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.btl_mmt1.models.PeerRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceDTO {
    @JsonProperty("infoHash")
    @NotBlank
    private String infoHash;
//    private String fileName;
//    private long fileSize;
    @JsonProperty("peerAddress")
    @NotBlank
    private String peerAddress;
    @JsonProperty("peerPort")
    @NotBlank
    private int peerPort;
    @JsonProperty("status")
    @NotBlank
    private StatusDTO status;

    public AnnounceDTO(PeerRole peerRole, String address, int port) {
    }
}

