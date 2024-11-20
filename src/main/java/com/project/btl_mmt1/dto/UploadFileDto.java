package com.project.btl_mmt1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDto {
    @JsonProperty("hashInfo")
    @NotBlank(message = "hashInfo is required")
    private String hashInfo;

    @JsonProperty("name")
    @NotBlank(message = "fileName is required")
    private String name;

    @JsonProperty("peerAddress")
    @NotBlank(message = "peerAddress is required")
    private String peerAddress;

    @JsonProperty("peerPort")
    @NotBlank(message = "peerPort is required")
    private int peerPort;

    @JsonProperty("size")
    @NotBlank(message = "fileSize is required")
    private long size;
}
