package com.project.btl_mmt1.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "peer_on_files")
public class PeerOnFile {
    @Id
    private String id;

    @DBRef
    private File fileId;

    @DBRef
    private Peer peerId;

}
