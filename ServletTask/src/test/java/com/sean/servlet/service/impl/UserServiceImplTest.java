package com.sean.servlet.service.impl;

import com.sean.servlet.model.User;
import com.sean.servlet.service.IUserService;
import com.sean.servlet.utils.DBUtils;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午6:55
 */
public class UserServiceImplTest {
    private IUserService userService;

    @Before
    public void setUp() throws Exception {
        dBInit();
        userService = UserServiceImpl.getInstance();

    }

    @Test
    public void testLogin() throws Exception {
        System.out.println(userService.login("sean1"));
    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testQueryUser() throws Exception {

    }

    private void dBInit() {
        Connection connection = null;
        Statement stmt = null;

        try {
            String createSQL = "drop table if exists user_info;" + "CREATE TABLE user_info("
                    + "id bigint(20) unsigned NOT NULL AUTO_INCREMENT ," + "name varchar(100) NOT NULL ,"
                    + "primary key(id)" + ");";

            connection = DBUtils.getConnection();
            stmt = connection.createStatement();
            stmt.execute(createSQL);
            String insertSql1 = "insert into user_info (name) values('sean1')";
            String insertSql2 = "insert into user_info (name) values('sean2')";
            stmt.execute(insertSql1);
            stmt.execute(insertSql2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(stmt);
            DBUtils.close(connection);
        }
    }
}
