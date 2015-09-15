package com.sean.servlet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sean.servlet.service.IUserService;
import com.sean.servlet.service.impl.UserServiceImpl;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-18 Time: 下午10:25
 */
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String id = request.getParameter("id");
        IUserService userService = UserServiceImpl.getInstance();
        int i = userService.delete(Integer.parseInt(id));
        if (i == 1)
            request.setAttribute("result", "delete success");
        else
            request.setAttribute("result", "delete fail");
        request.getRequestDispatcher("delResult.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
