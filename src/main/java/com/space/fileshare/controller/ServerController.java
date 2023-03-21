package com.space.fileshare.controller;

import com.space.fileshare.common.Result;
import com.space.fileshare.common.SessionManager;
import com.space.fileshare.support.server.Server;
import com.space.fileshare.support.server.server.Disk;
import com.space.fileshare.support.server.server.Sys;
import com.space.fileshare.util.RequestUtil;
import com.space.fileshare.vo.OnlineUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统信息
 *
 * @author vague 2022/5/3 14:49
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
    public List<Disk> getKernelInfo() {
        Server server = new Server();
        server.initDisk();
        return server.getDisk();
    }


    @GetMapping("/ip")
    public Result<String> getIp(HttpServletRequest request) {
        final String ip = new RequestUtil(request).getIp();
        return Result.success(ip);
    }


    @GetMapping("/online")
    public List<OnlineUser> getOnlineUsers() {
        return SessionManager.getAll();
    }


    @GetMapping("/sys")
    public Sys getSys() {
        Server server = new Server();
        server.initSysInfo();
        return server.getSys();
    }


}
