package com.project.btl_mmt1.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hashInfo;

    private String name;

    private long size;

    @ManyToMany(mappedBy = "files")
    private List<Peer> peers;
}
