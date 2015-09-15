package com.sean.servlet.controller;

import com.sean.servlet.service.IUserService;
import com.sean.servlet.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午3:28
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Cookie[] cookies = request.getCookies();
        {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("seanTraining") && cookie.getValue().equals("seanTraining")) {
                    request.setAttribute("username", "seanTraining");
                    request.setAttribute("cookieValue", cookie.getValue());
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                    return;
                }
            }
        }
        String username = request.getParameter("username");
        IUserService userService = UserServiceImpl.getInstance();
        if (userService.login(username)) {
            Cookie cookie = new Cookie("seanTraining", "seanTraining");
            response.addCookie(cookie);
            request.setAttribute("username", username);
            request.setAttribute("cookieValue", cookie.getValue());
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "user was not exist");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
