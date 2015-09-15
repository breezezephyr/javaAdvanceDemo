package com.sean.servlet.dao.impl;

import com.google.common.collect.Lists;
import com.sean.servlet.dao.UserDao;
import com.sean.servlet.model.User;
import com.sean.servlet.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午4:47
 */
public class UserDaoImpl implements UserDao {

    public int insert(String username) {
        int rowCount = 0;
        String insertSQL = "insert into user_info (name) values(?)";
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(insertSQL);
            pst.setString(1, username);
            rowCount = pst.executeUpdate();
            String status = (rowCount == 1) ? "success" : "fails";
            logger.info("insert username={} {}", username, status);
        } catch (SQLException e) {
            logger.error("insert error", e);
        } finally {
            DBUtils.close(pst);
            DBUtils.close(conn);
        }
        return rowCount;
    }

    public int delete(int userId) {
        int rowCount = 0;
        String deleteSQL = "delete from user_info where id = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(deleteSQL);
            pst.setInt(1, userId);
            rowCount = pst.executeUpdate();
            String status = (rowCount == 1) ? "success" : "fails";
            logger.info("delete id={} {}", userId, status);
        } catch (SQLException e) {
            logger.error("delete error", e);
        } finally {
            DBUtils.close(pst);
            DBUtils.close(conn);
        }
        return rowCount;
    }

    public int update(User user) {
        String updateSQL = "update user_info set name = ? where id = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        int rowCount = 0;
        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(updateSQL);
            pst.setString(1, user.getUsername());
            pst.setInt(2, user.getId());
            rowCount = pst.executeUpdate();
            String status = (rowCount == 1) ? "success" : "fails";
            logger.info("update {} {}", user, status);
        } catch (SQLException e) {
            logger.error("update error", e);
        } finally {
            DBUtils.close(pst);
            DBUtils.close(conn);
        }
        return 1;
    }

    public List<User> query() {
        String selectSQL = "select * from user_info";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<User> users = Lists.newArrayList();
        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(selectSQL);
            rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("query all error", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pst);
            DBUtils.close(conn);
        }
        logger.info("find {} users in table", users.size());
        return users;
    }

    public User query(int userId) {
        String selectSQL = "select * from user_info where id = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        User user = new User();
        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(selectSQL);
            pst.setInt(1, userId);
            rs = pst.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                logger.info("query {} success", user);
            }
        } catch (SQLException e) {
            logger.error("query error", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pst);
            DBUtils.close(conn);
        }

        return user;
    }

    @Override
    public User query(String username) {
        String selectSQL = "select * from user_info where name = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        User user = new User();
        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(selectSQL);
            pst.setString(1, username);
            rs = pst.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                logger.info("query {} success", user);
            }
        } catch (SQLException e) {
            logger.error("query error", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pst);
            DBUtils.close(conn);
        }

        return user;
    }
}
