package com.project.btl_mmt1.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "files")
public class File {
    @Id
    private String id;

    @Indexed(unique = true)
    private String hashInfo;

    private String name;

    private long size;

    @DBRef(lazy = true)
    private User user;

    @DBRef(lazy = true)
    private List<Peer> peers;
}

