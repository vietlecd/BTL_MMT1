package com.project.btl_mmt1.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "files")
public class File {
    @Id
    private String id;
    private String hashInfo;
    private String name;
    private String trackerUrl;
    private long size;

}

