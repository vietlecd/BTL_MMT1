package com.project.btl_mmt1.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "peers")
public class Peer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private int port;

    @ManyToMany
    @JoinTable(
            name = "file_peers",
            joinColumns = @JoinColumn(name = "peer_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private List<File> files;
}
