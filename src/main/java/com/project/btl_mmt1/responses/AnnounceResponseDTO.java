package com.project.btl_mmt1.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
public class AnnounceResponseDTO {
    @JsonProperty("file_name")
    private String file_name;

    @JsonProperty("size")
    private String size;

    @JsonProperty("address")
    private String address;
}
