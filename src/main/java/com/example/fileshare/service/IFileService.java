package com.example.fileshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fileshare.mapper.FileMapper;
import com.example.fileshare.vo.EditVo;
import com.example.fileshare.vo.FileVo;
import com.example.fileshare.vo.ImgVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/4/29 14:33
 */
public interface IFileService extends IService<FileVo> {

    int TYPE_FOLDER = 1;

    int TYPE_FILE = 2;

    List<FileVo> searchFolder(String folderPath);


    void storeFile(MultipartFile file, String path) throws IOException;


    String createDirectory(String filePath, String directoryName);

    String removeFile(FileVo fileVo);

    void view(String filePath, HttpServletResponse response);

    ImgVo storeImgFile(MultipartFile file) throws IOException;

    int saveEdit(String content);

    EditVo getEdit();
}
