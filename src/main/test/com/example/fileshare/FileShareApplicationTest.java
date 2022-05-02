package com.example.fileshare;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.fileshare.service.IFileService;
import com.example.fileshare.vo.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: ZJM
 * @Date: 2022/5/1 22:34
 * @Description:
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class FileShareApplicationTest {


    @Autowired
    private IFileService fileService;

    @Test
    public void list(){
        final List<FileVo> list = fileService.list();
        System.out.println(list.size());
        System.out.println(list);
    }


    @Test
    public void clear(){
        fileService.remove(new LambdaQueryWrapper<>());
        list();
    }



    @Test
    public void select(){
        LambdaQueryWrapper<FileVo> queryWrapper = new LambdaQueryWrapper<>();
        //   D:\sdsk\dev\project\file-share/file\
        queryWrapper.eq(FileVo::getFilePath,"D:\\sdsk\\dev\\project\\file-share/file\\");
        final List<FileVo> list = fileService.list(queryWrapper);
        System.out.println(list.size());
        System.out.println(list);
    }

}
