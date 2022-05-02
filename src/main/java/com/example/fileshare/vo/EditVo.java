package com.example.fileshare.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/5/2 19:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_edit")
public class EditVo {

    private Long id;
    private String content;

}
