package com.project.btl_mmt1.repositories;

import com.project.btl_mmt1.entity.File;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<File, String> {
    Optional<File> findByHashInfo(String hashInfo);

}
