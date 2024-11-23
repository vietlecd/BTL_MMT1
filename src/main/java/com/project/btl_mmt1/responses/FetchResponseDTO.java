package com.project.btl_mmt1.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FetchResponseDTO {
    private String address;
    private long port;
    private String fullName;
    private String fileName;
    private long fileSize;
}
