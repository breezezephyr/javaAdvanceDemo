package com.sean.servlet.controller;

import com.sean.servlet.model.User;
import com.sean.servlet.service.IUserService;
import com.sean.servlet.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-18 Time: 下午10:25
 */
public class InsertServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String name = request.getParameter("name");
        IUserService userService = UserServiceImpl.getInstance();
        int i = userService.addUser(name);
        if (i == 1)
            request.setAttribute("result", "insert success");
        else
            request.setAttribute("result", "insert fail");
        request.getRequestDispatcher("insertResult.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
