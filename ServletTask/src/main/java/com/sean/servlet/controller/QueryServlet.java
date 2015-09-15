package com.sean.servlet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.sean.servlet.model.User;
import com.sean.servlet.service.IUserService;
import com.sean.servlet.service.impl.UserServiceImpl;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-18 Time: 下午10:25
 */
public class QueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        IUserService userService = UserServiceImpl.getInstance();
        String id = request.getParameter("id");
        if (id != null) {

            User user = userService.queryUser(Integer.parseInt(id));
            List<User> users = Lists.newArrayList();
            users.add(user);
            if (user.getUsername() != null) {
                request.setAttribute("result", "UserQuery");
                request.setAttribute("users", users);
            } else {
                request.setAttribute("result", "query fail");
            }
            request.getRequestDispatcher("queryResult.jsp").forward(request, response);
        } else {
            List<User> users = userService.getAll();
            request.setAttribute("result", "QueryAll");
            request.setAttribute("users", users);
            request.getRequestDispatcher("queryResult.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
