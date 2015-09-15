package com.sean.servlet.dao.impl;

import com.sean.servlet.dao.UserDao;
import com.sean.servlet.model.User;
import com.sean.servlet.utils.DBUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午5:29
 */
public class UserDaoImplTest {
    UserDao userDao = new UserDaoImpl();;

    @BeforeClass
    public static void setUp() throws Exception {
        new UserDaoImplTest().dBInit();
    }

    @Test
    public void testInsert() throws Exception {
        User user = new User(3, "sean");
        userDao.insert("sean");
        assertTrue(user.equals(userDao.query("sean")));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User(4, "Lucy");
        userDao.insert("Lucy");
        assertEquals(1, userDao.delete(4));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(2, "update");
        assertEquals(1, userDao.update(user));
    }

    @Test
    public void testQuery() throws Exception {
        assertEquals(3, userDao.query().size());
    }

    @Test
    public void testQueryUser() throws Exception {
        User user = new User(1, "sean");
        assertTrue(user.equals(userDao.query("sean")));
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
            String insertSql1 = "insert into user_info (name) values('sean')";
            String insertSql2 = "insert into user_info (name) values('sean')";
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