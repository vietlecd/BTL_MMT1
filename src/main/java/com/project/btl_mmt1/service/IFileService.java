package com.project.btl_mmt1.service;

import com.project.btl_mmt1.dto.AnnounceDTO;
import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.models.File;
import com.project.btl_mmt1.models.Peer;
import com.project.btl_mmt1.models.PeerOnFile;
import com.project.btl_mmt1.models.User;
import com.project.btl_mmt1.responses.FileResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFileService {
    List<?> find_all();
    List<?> search(String hashInfo);

    FileResponseDto create(UploadFileDto dto, User user);

}
