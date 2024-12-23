package com.project.btl_mmt1.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDto {
    private String fileName;
    private String hashInfo;
    private long size;
}
