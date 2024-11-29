package com.project.btl_mmt1.service;

import com.project.btl_mmt1.dto.UploadFileDto;
import com.project.btl_mmt1.responses.FetchResponseDTO;
import com.project.btl_mmt1.responses.FileResponseDto;

import java.util.List;

public interface IFileService {
    List<?> find_all();
    FetchResponseDTO search(String hashInfo);

    FileResponseDto create(UploadFileDto dto);

}
