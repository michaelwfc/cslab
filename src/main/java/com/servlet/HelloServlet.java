package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// WebServlet注解表示这是一个Servlet，并映射到地址/:
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String name = req.getParameter("name");
            if (name == null) {
                name = "Java";
            }

            resp.setContentType("text/html");

            PrintWriter pw = resp.getWriter();
            pw.write("<h1>Hello " + name + "!</h1>");
            pw.flush();
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }

}
