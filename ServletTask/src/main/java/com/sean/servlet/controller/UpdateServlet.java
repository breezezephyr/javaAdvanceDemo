package com.sean.servlet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sean.servlet.model.User;
import com.sean.servlet.service.IUserService;
import com.sean.servlet.service.impl.UserServiceImpl;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-18 Time: 下午10:25
 */
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        User user = new User(Integer.parseInt(id), name);
        IUserService userService = UserServiceImpl.getInstance();
        int i = userService.updateUser(user);
        if (i == 1)
            request.setAttribute("result", "update success");
        else
            request.setAttribute("result", "update fail");
        request.getRequestDispatcher("insertResult.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
