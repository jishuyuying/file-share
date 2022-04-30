package com.example.fileshare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/4/29 11:40
 */
@Controller
@RequestMapping("/index")
public class IndexController {


    @RequestMapping("/home")
    public String index() {
        return "home";
    }


}
