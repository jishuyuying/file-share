package com.example.fileshare.common;

/**
 * @Author: vague
 * @Date: 2021/9/30 15:34
 * @Description:
 */
public interface BaseErrorInfoInterface {

    /**
     * 错误码
     * @return
     */
    int getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}
