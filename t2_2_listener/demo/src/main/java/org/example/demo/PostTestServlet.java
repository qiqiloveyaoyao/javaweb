package org.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/postTest")
public class PostTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        // 获取请求参数
        String paramValue = request.getParameter("param");

        // 响应发送HTML内容
        PrintWriter out = response.getWriter();
        out.println("<h1>POST请求已接收</h1>");
        out.println("<p>参数值为：" + paramValue + "</p>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 重定向到表单页面
        response.sendRedirect("postTestForm.jsp");
    }
}