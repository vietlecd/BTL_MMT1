package com.project.btl_mmt1.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDto {
    private String hashInfo;
    private String name;
    private String peerAddress;
    private int peerPort;
    private long size;
}
