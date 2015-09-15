package com.sean.servlet.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午3:30
 */
public class CookieVerifyFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("seanTraining")) {
                if (cookie.getValue().equals("seanTraining")) {
                    request.setAttribute("cookieValue", cookie.getValue());
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        req.setAttribute("message", "请重新登录后再试");
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
