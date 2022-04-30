package com.example.fileshare.service;

import com.example.fileshare.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/4/29 14:33
 */
public interface IFileService {

    int TYPE_FOLDER = 1;

    int TYPE_FILE = 2;

    List<FileVo> searchFolder();


    void storeFile(MultipartFile file, String path) throws IOException;


}
