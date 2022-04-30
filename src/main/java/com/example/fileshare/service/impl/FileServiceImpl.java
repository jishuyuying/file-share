package com.example.fileshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fileshare.mapper.FileMapper;
import com.example.fileshare.service.IFileService;
import com.example.fileshare.vo.FileVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/4/29 14:34
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileVo> implements IFileService {

    @Value("${file-share.file-path:#{null}}")
    private String fileRootPath;


    @Override
    public List<FileVo> searchFolder() {
        File file = new File(this.getFilePath());
        if(!file.exists()){
            file.mkdir();
        }
        File[] files = file.listFiles();
        if(Objects.isNull(files)){
            return Collections.emptyList();
        }
        List<FileVo> list = this.listByFolderPath(this.getFilePath());
        List<FileVo> res = new ArrayList<>();
        for (File tFile : files) {
            FileVo fileVo = new FileVo();
            for (FileVo vo : list) {
                if(vo.getFileName().equals(tFile.getName())){
                    BeanUtils.copyProperties(vo, fileVo);
                    break;
                }
            }
            fileVo.setFileName(tFile.getName());
            if(tFile.isDirectory()){
                fileVo.setType(TYPE_FOLDER);
            }else {
                fileVo.setType(TYPE_FILE);
            }
            res.add(fileVo);
        }
        return res.stream().sorted(Comparator.comparing(FileVo::getType)).collect(Collectors.toList());
    }

    @Override
    public void storeFile(MultipartFile file, String folderPath) throws IOException {
        if(null == folderPath){
            folderPath = "";
        }
        String targetPath = this.getFilePath() + File.separatorChar + folderPath;
        String filename = file.getOriginalFilename();
        if(!StringUtils.hasText(filename)){
            throw new RuntimeException("错误的文件名");
        }
        FileUtils.copyFile(Objects.requireNonNull(MultipartFileToFile(file)), new File(targetPath, filename));
        FileVo fileVo = new FileVo();
        fileVo.setFileName(filename);
        fileVo.setFilePath(targetPath);
        fileVo.setCreateTime("2022-01-01 12:23:23");
        fileVo.setType(TYPE_FILE);
        fileVo.setId(new Date().getTime());
        this.save(fileVo);
    }

    /**
     * 将MultipartFile转换为File
     * @param multiFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        assert fileName != null;
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<FileVo> listByFolderPath(String fileRootPath) {
        LambdaQueryWrapper<FileVo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FileVo::getFilePath, fileRootPath);
        return this.list(lambdaQueryWrapper);
    }


    private String getFilePath(){
        if(StringUtils.hasText(fileRootPath)){
            return fileRootPath + "/file";
        }
        return System.getProperty("user.dir") + "/file";
    }


}
