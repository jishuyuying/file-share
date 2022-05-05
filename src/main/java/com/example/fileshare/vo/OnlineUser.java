package com.example.fileshare.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 5/5/2022 下午 5:56
 */
@Data
@Accessors(chain = true)
public class OnlineUser {

    private String ip;

    private String lastLoginTime;

}
