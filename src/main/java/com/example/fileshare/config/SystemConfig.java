package com.example.fileshare.config;

import com.example.fileshare.common.SystemInfo;
import com.sun.management.OperatingSystemMXBean;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import sun.dc.pr.PRError;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/5/3 13:59
 */
@Configuration
public class SystemConfig {


    public SystemInfo getSystemInfo() {
        /**
         * String osName = System.getProperty("os.name"); //操作系统名称
         * String osArch = System.getProperty("os.arch"); //操作系统构架
         * String osVersion = System.getProperty("os.version"); //操作系统版本
         *
         *
         * java.version	Java 运行时环境版本
         * java.vendor	Java 运行时环境供应商
         * java.vendor.url	Java 供应商的 URL
         * java.home	Java 安装目录
         * java.vm.specification.version	Java 虚拟机规范版本
         * java.vm.specification.vendor	Java 虚拟机规范供应商
         * java.vm.specification.name	Java 虚拟机规范名称
         * java.vm.version	Java 虚拟机实现版本
         * java.vm.vendor	Java 虚拟机实现供应商
         * java.vm.name	Java 虚拟机实现名称
         * java.specification.version	Java 运行时环境规范版本
         * java.specification.vendor	Java 运行时环境规范供应商
         * java.specification.name	Java 运行时环境规范名称
         * java.class.version	Java 类格式版本号
         * java.class.path	Java 类路径
         * java.library.path	加载库时搜索的路径列表
         * java.io.tmpdir	默认的临时文件路径
         * java.compiler	要使用的 JIT 编译器的名称
         * java.ext.dirs	一个或多个扩展目录的路径
         * os.name	操作系统的名称
         * os.arch	操作系统的架构
         * os.version	操作系统的版本
         * file.separator	文件分隔符（在 UNIX 系统中是“/”）
         * path.separator	路径分隔符（在 UNIX 系统中是“:”）
         * line.separator	行分隔符（在 UNIX 系统中是“/n”）
         * user.name	用户的账户名称
         * user.home	用户的主目录
         * user.dir	用户的当前工作目录
         */
        return new SystemInfo().setOsName(getProperty("os.name")).
                setOsArch(getProperty("os.arch")).
                setOsVersion(getProperty("os.version")).
                setJavaHome(getProperty("java.home")).
                setJavaVersion(getProperty("java.version")).
                setFileSeparator(getProperty("file.separator")).
                setPathSeparator(getProperty("path.separator")).
                setLineSeparator(getProperty("line.separator")).
                setUserDir(getProperty("user.dir")).
                setUserHome(getProperty("user.home")).
                setUserName(getProperty("user.name"));
    }


    private static String getProperty(String key) {
        return System.getProperty(key);
    }



}
