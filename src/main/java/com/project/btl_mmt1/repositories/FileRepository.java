package com.project.btl_mmt1.repositories;

import com.project.btl_mmt1.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByHashInfo(String hashInfo);

}
