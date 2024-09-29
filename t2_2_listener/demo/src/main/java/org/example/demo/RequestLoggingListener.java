package org.example.demo;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

// 使用@WebListener注解标注该类为一个ServletContext监听器
@WebListener
public class RequestLoggingListener implements ServletRequestListener {

    // 创建一个Logger对象用于记录日志信息
    private static final Logger LOGGER = Logger.getLogger(RequestLoggingListener.class.getName());

    // 当ServletRequest事件发生时，这个方法会被调用
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 从事件对象中获取HttpServletRequest对象
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        // 记录请求开始的时间，并将其存储在请求属性中
        request.setAttribute("startTime", System.currentTimeMillis());
    }

    // 当ServletRequest事件结束时，这个方法会被调用
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 从事件对象中获取HttpServletRequest对象
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        // 从请求属性中获取之前存储的开始时间
        long startTime = (Long) request.getAttribute("startTime");
        // 计算请求处理的持续时间
        long duration = System.currentTimeMillis() - startTime;

        // 调用logRequestDetails方法记录请求的详细信息
        logRequestDetails(request, duration);
    }

    // 定义一个私有方法用于记录请求的详细信息
    private void logRequestDetails(HttpServletRequest request, long duration) {
        // 获取客户端的IP地址
        String clientIp = request.getRemoteAddr();
        // 获取请求的方法（如GET或POST）
        String requestMethod = request.getMethod();
        // 获取请求的URI
        String requestUri = request.getRequestURI();
        // 获取请求的查询字符串
        String queryString = request.getQueryString();
        // 获取请求的用户代理信息
        String userAgent = request.getHeader("User-Agent");

        // 记录请求发生的时间
        LOGGER.info(() -> "请求时间: " + new java.util.Date());
        // 记录客户端IP地址
        LOGGER.info(() -> "客户端IP: " + clientIp);
        // 记录请求方法
        LOGGER.info(() -> "请求方法: " + requestMethod);
        // 记录请求URI
        LOGGER.info(() -> "请求URI: " + requestUri);
        // 记录查询字符串
        LOGGER.info(() -> "查询字符串: " + queryString);
        // 记录用户代理信息
        LOGGER.info(() -> "用户代理: " + userAgent);
        // 记录请求的处理时间
        LOGGER.info(() -> "请求处理时间: " + duration + " 毫秒");
    }
}