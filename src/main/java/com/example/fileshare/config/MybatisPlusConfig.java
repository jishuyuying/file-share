package com.example.fileshare.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/7/23 12:07
 */
//@MapperScan("com.example.fileshare.mapper.*")
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 这里就是分页插件的配置了，由于由@Configuration注解，所以是自动注入的，自动应用
        return new PaginationInterceptor();
    }



}
