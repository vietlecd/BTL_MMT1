package com.project.btl_mmt1.models;

import lombok.*;
import jakarta.persistence.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "peer_on_files")
public class PeerOnFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false, referencedColumnName = "id")
    private File fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peer_id", nullable = false, referencedColumnName = "id")
    private Peer peerId;

    @Enumerated(EnumType.STRING)
    private PeerRole peerRole;
}
