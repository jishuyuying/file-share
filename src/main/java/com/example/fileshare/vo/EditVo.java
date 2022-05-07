package com.example.fileshare.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/5/2 19:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_edit")
public class EditVo implements Serializable {

    private Long id;
    private String content;

}
