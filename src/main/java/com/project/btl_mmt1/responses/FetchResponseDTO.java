package com.project.btl_mmt1.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class FetchResponseDTO {
//    private String address;
//    private long port;

    private List<Map<String, Object>> peers;
    private String fullName;
    private String fileName;
    private long fileSize;
}
