package com.example.fileshare.config;

import com.example.fileshare.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 5/5/2022 下午 2:01
 */

@Slf4j
@Component
@WebFilter(urlPatterns = {"/**"}, filterName = "tokenAuthorFilter")
public class ServerFilter implements Filter {

    private final CopyOnWriteArraySet<String> ipSet = new CopyOnWriteArraySet<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TokenFilter init {}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ip = new RequestUtil((HttpServletRequest)request).getIp();
        log.info(ip);
        ipSet.add(ip);
        // 到下一个链
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("TokenFilter destroy");
    }


}
