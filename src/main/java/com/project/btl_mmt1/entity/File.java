package com.project.btl_mmt1.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "files")
public class File {
    @Id
    private String id;
    private String hashInfo;
    private String name;
    private String trackerUrl;
    private long size;

}

