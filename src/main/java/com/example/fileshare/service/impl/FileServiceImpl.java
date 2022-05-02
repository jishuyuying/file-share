package com.example.fileshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fileshare.common.BizException;
import com.example.fileshare.mapper.EditMapper;
import com.example.fileshare.mapper.FileMapper;
import com.example.fileshare.service.IFileService;
import com.example.fileshare.vo.EditVo;
import com.example.fileshare.vo.FileVo;
import com.example.fileshare.vo.ImgVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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


    @Autowired
    private EditMapper editMapper;

    @Override
    public List<FileVo> searchFolder() {
        File file = new File(this.getFilePath());
        if (!file.exists()) {
            file.mkdir();
        }
        File[] files = file.listFiles();
        if (Objects.isNull(files)) {
            return Collections.emptyList();
        }
        final String filePath = this.getFilePath();
        List<FileVo> list = this.listByFolderPath(filePath);
        List<FileVo> res = new ArrayList<>();
        for (File tFile : files) {
            FileVo fileVo = new FileVo();
            for (FileVo vo : list) {
                if (vo.getFileName().equals(tFile.getName())) {
                    BeanUtils.copyProperties(vo, fileVo);
                    break;
                }
            }
            fileVo.setFileName(tFile.getName());
            if (tFile.isDirectory()) {
                fileVo.setType(TYPE_FOLDER);
                fileVo.setFileSize("-");
            } else {
                String size = String.format("%.2f", (double) tFile.length() / 1024 / 1024);
                fileVo.setFileSize(size + "MB");
                fileVo.setType(TYPE_FILE);
            }
            fileVo.setFilePath(filePath);
            fileVo.setCreateName("admin");
            res.add(fileVo);
        }
        return res.stream().sorted(Comparator.comparing(FileVo::getType)).collect(Collectors.toList());
    }

    @Override
    public void storeFile(MultipartFile file, String folderPath) throws IOException {
        String targetPath;
        if (!StringUtils.hasText(folderPath)) {
            targetPath = this.getFilePath();
        } else {
            targetPath = this.getFilePath() + File.separatorChar + folderPath;
        }
        String filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
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

    @Override
    public String createDirectory(String directoryName) {
        if (!StringUtils.hasText(directoryName)) {
            throw new BizException("文件名不可为空");
        }
        if (this.checkFileExisted(directoryName)) {
            throw new BizException("文件夹已存在");
        }
        if (new File(this.getFilePath() + File.separatorChar + directoryName).mkdir()) {
            return "文件夹创建成功";
        }
        return "文件夹创建失败";
    }

    @Override
    public String removeFile(FileVo fileVo) {
        if (Objects.nonNull(fileVo)) {
            if (StringUtils.hasText(fileVo.getFilePath())) {
                File file = new File(fileVo.getFilePath());
                if (file.exists() && file.isDirectory()) {
                    final File[] files = file.listFiles();
                    if (Objects.nonNull(files)) {
                        for (File tFile : files) {
                            if (tFile.getName().equals(fileVo.getFileName())) {
                                if ((TYPE_FILE == fileVo.getType() && tFile.isFile()) ||
                                        (TYPE_FOLDER == fileVo.getType() && tFile.isDirectory())) {
                                    if (tFile.delete()) {
                                        return "删除成功";
                                    }
                                    return "删除失败";
                                }
                            }
                        }
                    }
                }
            }
        }
        return "删除失败";
    }

    @Override
    public void view(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        //PDF文件地址
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input=null;
            try {
                input= new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                // do nothing
            }finally{
                try {
                    if(input!=null){
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public ImgVo storeImgFile(MultipartFile file) throws IOException {
        String targetPath = this.getFilePath() + File.separatorChar + "img";
        String filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            throw new BizException("错误的文件名");
        }
        FileUtils.copyFile(Objects.requireNonNull(MultipartFileToFile(file)), new File(targetPath, filename));
        FileVo fileVo = new FileVo();
        fileVo.setFileName(filename);
        fileVo.setFilePath(targetPath);
        fileVo.setCreateTime("2022-01-01 12:23:23");
        fileVo.setType(TYPE_FILE);
        fileVo.setId(new Date().getTime());
        this.save(fileVo);
        ImgVo imgVo = new ImgVo();
        imgVo.setSrc("/file/view?filePath=" + targetPath + File.separatorChar + filename);
        imgVo.setTitle(filename);
        return imgVo;
    }

    @Override
    public int saveEdit(String content) {
        EditVo editVo = new EditVo();
        editVo.setId(new Date().getTime());
        editVo.setContent(content);
        return editMapper.insert(editVo);
    }

    @Override
    public EditVo getEdit() {
        LambdaQueryWrapper<EditVo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EditVo::getId).last("limit 1");
        return editMapper.selectOne(queryWrapper);
    }

    private boolean checkFileExisted(String directoryName) {
        return new File(this.getFilePath() + File.separatorChar + directoryName).exists();
    }


    /**
     * 将MultipartFile转换为File
     *
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


    private String getFilePath() {
        if (StringUtils.hasText(fileRootPath)) {
            return fileRootPath + "/file";
        }
        return System.getProperty("user.dir") + "/file";
    }


}
