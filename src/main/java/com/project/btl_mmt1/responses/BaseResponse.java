package com.project.btl_mmt1.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class BaseResponse {
    private boolean success;
    private String message;
    private String data;
}
