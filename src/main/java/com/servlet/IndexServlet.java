package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.tomcat.util.http.parser.Cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// WebServlet注解表示这是一个Servlet，并映射到地址/:
@WebServlet(urlPatterns = "/servlet")
public class IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String user = (String) req.getSession().getAttribute("user");

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("X-Powered-By", "JavaEE Servlet");

            PrintWriter pw = resp.getWriter();
            pw.write("<h1>Welcom " + (user != null ? user : "Guest") + "</h1>");
            if (user == null) {
                // 未登录，显示登录链接:
                pw.write("<p><a href=\"/signin\">Sign In</a></p>");
            } else {
                // 已登录，显示登出链接:
                pw.write("<p><a href=\"/signout\">Sign Out</a></p>");
            }

            pw.flush();
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }

    private String parseLanguageFromCookie(HttpServletRequest req) {
        // 获取请求附带的所有Cookie:
        Cookie[] cookies = req.getCookies();
        // 如果获取到Cookie:
        if (cookies != null) {
            // 循环每个Cookie:
            for (Cookie cookie : cookies) {
                // 如果Cookie名称为lang:
                if (cookie.getName().equals("lang")) {
                    // 返回Cookie的值:
                    return cookie.getValue();
                }
            }
        }
        // 返回默认值:
        return "en";
    }
}