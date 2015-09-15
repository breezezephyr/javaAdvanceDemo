package com.sean.mobile.training.dao.impl;

import com.sean.mobile.training.bean.User;
import com.sean.mobile.training.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class OracleUserDao implements UserDao {
    @Override
    public void saveUser(User user) {
        System.out.println("save user to Oracle!");
    }
}
