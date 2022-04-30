package com.example.fileshare.controller;

import com.example.fileshare.common.Result;
import com.example.fileshare.service.IFileService;
import com.example.fileshare.vo.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/4/29 14:32
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file, String path) {
        try {
            fileService.storeFile(file, path);
            return Result.success();
        }catch (IOException e){
            return Result.failure(e.getMessage());
        }

    }

    @GetMapping("/searchFolder")
    public Result<List<FileVo>> searchFolder(){
        return Result.success(fileService.searchFolder());
    }

}
