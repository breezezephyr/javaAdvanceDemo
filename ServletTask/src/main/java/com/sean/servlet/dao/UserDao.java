package com.sean.servlet.dao;

import com.sean.servlet.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午4:44
 */
public interface UserDao {
    public final static Logger logger = LoggerFactory.getLogger(UserDao.class);

    public int insert(String username);

    public int delete(int userId);

    public int update(User user);

    public List<User> query();

    public User query(int userId);

    public User query(String username);
}
