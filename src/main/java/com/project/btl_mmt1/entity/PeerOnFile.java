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
@Document(collection = "peer_on_files")
public class PeerOnFile {
    @Id
    private String id;
    private String fileId;
    private String peerId;

}
