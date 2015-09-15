package com.sean.mobile.training.service;

import com.sean.mobile.training.bean.User;
import com.sean.mobile.training.dao.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Qualifier("userdao")
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user) {
        userDao.saveUser(user);
    }
}
