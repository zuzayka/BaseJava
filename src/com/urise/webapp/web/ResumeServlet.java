package com.urise.webapp.web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(!Objects.equals(name, "resume") ? "Hello resumes!" : """
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <title>Table resume</title>
                            </head>
                            <body>
                                <table border = "2">
                                    <tr>
                                        <th>uuid</th><th>full name</th>
                                    </tr>
                                    <tr>
                                        <td>uuid1</td><td>Ivanov Ivan</td>
                                    </tr>
                                    <tr>
                                        <td>uuid2</td><td>Petrov Petr</td>
                                    </tr>
                                    <tr>
                                        <td>uuid3</td><td>Saidova Zuleykha</td>
                                    </tr>
                                </table>
                            </body>
                            </html>
                """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
