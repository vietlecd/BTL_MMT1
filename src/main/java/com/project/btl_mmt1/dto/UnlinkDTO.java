package com.project.btl_mmt1.dto;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnlinkDTO {
    String infoHash;
    String address;
    Integer port;
}
