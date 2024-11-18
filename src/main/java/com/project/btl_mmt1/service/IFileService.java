package com.project.btl_mmt1.service;

import com.project.btl_mmt1.dto.CreatePeerOnFileDto;
import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.entity.File;
import com.project.btl_mmt1.entity.PeerOnFile;

import java.util.List;

public interface IFileService {
    List<File> search(String hashInfo);

    File create(UploadFileDto dto);

    PeerOnFile createPOFByInfoHashAndPeerAddress(CreatePeerOnFileDto dto);
}
