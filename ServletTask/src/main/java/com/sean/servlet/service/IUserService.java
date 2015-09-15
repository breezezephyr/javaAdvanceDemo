package com.sean.servlet.service;

import com.sean.servlet.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午6:09
 */
public interface IUserService {
    public boolean login(String username);

    public List<User> getAll();

    public int addUser(String username);

    public int updateUser(User user);

    public int delete(int userId);

    public User queryUser(int userId);
}
