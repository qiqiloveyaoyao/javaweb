package org.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteTest")
public class DeleteTestServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        // 获取请求参数
        String id = request.getParameter("id");

        // 响应发送HTML内容
        PrintWriter out = response.getWriter();
        out.println("<h1>DELETE请求已接收</h1>");
        out.println("<p>删除的ID为：" + id + "</p>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查是否为模拟的DELETE请求
        String method = request.getHeader("X-HTTP-Method-Override");
        if ("DELETE".equals(method)) {
            // 处理DELETE请求
            doDelete(request, response);
        } else {
            // 处理普通的POST请求（重定向到表单页面）
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 重定向到表单页面
        response.sendRedirect("deleteTestForm.jsp");
    }
}