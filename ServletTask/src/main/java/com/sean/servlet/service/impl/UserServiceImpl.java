package com.sean.servlet.service.impl;

import com.sean.servlet.dao.UserDao;
import com.sean.servlet.dao.impl.UserDaoImpl;
import com.sean.servlet.model.User;
import com.sean.servlet.service.IUserService;

import java.util.List;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午6:14
 */
public class UserServiceImpl implements IUserService {
    private static UserServiceImpl instance = null;
    private static UserDao userDao;
    static {
        instance = new UserServiceImpl();
    }

    private UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean login(String username) {
        if (userDao.query(username).getUsername() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        return userDao.query();
    }

    public int addUser(String username) {
        return userDao.insert(username);
    }

    @Override
    public int updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(int userId) {
        return userDao.delete(userId);
    }

    @Override
    public User queryUser(int userId) {
        return userDao.query(userId);
    }
}
