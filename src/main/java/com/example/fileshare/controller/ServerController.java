package com.example.fileshare.controller;

import com.example.fileshare.common.Result;
import com.example.fileshare.support.server.Server;
import com.example.fileshare.support.server.server.Disk;
import com.example.fileshare.util.RequestUtil;
import com.example.fileshare.vo.OnlineUser;
import com.example.fileshare.websocket.WsSessionManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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


    @GetMapping("/online")
    public List<OnlineUser> getOnlineUsers() throws Exception {
        final ConcurrentMap<String, WebSocketSession> sessionPool = WsSessionManager.getSessionPool();
        return sessionPool.keySet().stream().map(s -> new OnlineUser().setIp(s)).collect(Collectors.toList());
    }


}
