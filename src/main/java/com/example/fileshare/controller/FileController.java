package com.example.fileshare.controller;

import com.example.fileshare.common.Result;
import com.example.fileshare.service.IFileService;
import com.example.fileshare.vo.EditVo;
import com.example.fileshare.vo.FileVo;
import com.example.fileshare.vo.ImgVo;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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


    @PostMapping("/img/upload")
    public Result<ImgVo> uploadImg(MultipartFile file) {
        try {
            return Result.success(fileService.storeImgFile(file));
        }catch (IOException e){
            return Result.failure(e.getMessage());
        }

    }

    @GetMapping("/searchFolder")
    public Result<List<FileVo>> searchFolder(String folderPath){
        return Result.success(fileService.searchFolder(folderPath));
    }


    @GetMapping("/createDirectory")
    public Result<String> createDirectory(String filePath, String directoryName){
        return Result.success(fileService.createDirectory(filePath, directoryName));
    }


    @GetMapping("/view")
    public void view(String filePath, HttpServletResponse response){
        fileService.view(filePath, response);
    }



    @PostMapping("/removeFile")
    public Result<String> removeFile(@RequestBody FileVo fileVo){
        return Result.success(fileService.removeFile(fileVo));
    }


    @PostMapping("/saveEdit")
    public Result<String> saveEdit(String content){
        return Result.success(fileService.saveEdit(content));
    }


    @GetMapping("/getEdit")
    public Result<EditVo> getEdit(){
        return Result.success(fileService.getEdit());
    }

}
