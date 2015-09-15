package com.sean.mobile.training;

import com.sean.mobile.training.aop.LogHandler;
import com.sean.mobile.training.bean.User;
import com.sean.mobile.training.dao.UserDao;
import com.sean.mobile.training.dao.impl.MysqlUserDao;
import com.sean.mobile.training.service.UserService;

import java.lang.reflect.Proxy;

public class JDKAopTest {
    public static void main(String[] args) {
        User user = new User();
        user.setName("hanmeimei");

        UserDao userDao = new MysqlUserDao();

        UserDao daoProxy = (UserDao)Proxy.newProxyInstance(userDao.getClass().getClassLoader(), new Class[]{UserDao.class}, new LogHandler(userDao));

        UserService service = new UserService();
        service.setUserDao(daoProxy);
        service.addUser(user);
    }
}
