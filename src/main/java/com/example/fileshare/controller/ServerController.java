package com.example.fileshare.controller;

import com.example.fileshare.common.Result;
import com.example.fileshare.support.server.Server;
import com.example.fileshare.support.server.server.Disk;
import com.example.fileshare.util.RequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 2022/5/3 14:49
 */
@RestController
@RequestMapping("/server")
public class ServerController {


    @GetMapping("/info")
    public Server getServerInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return server;
    }


    @GetMapping("/kernel")
    public List<Disk> getKernelInfo() throws Exception {
        Server server = new Server();
        server.initDisk();
        return server.getDisk();
    }


    @GetMapping("/ip")
    public Result<String> getIp(HttpServletRequest request) throws Exception {
        final String ip = new RequestUtil(request).getIp();
        return Result.success(ip);
    }




}
