package com.project.btl_mmt1.service;

import com.project.btl_mmt1.entity.File;

import java.util.List;

public interface IFileService {
    List<File> search(String hashInfo);
}
