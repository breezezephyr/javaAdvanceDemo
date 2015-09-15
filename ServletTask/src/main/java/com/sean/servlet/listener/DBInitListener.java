package com.sean.servlet.listener;

import com.sean.servlet.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午3:32
 */
public class DBInitListener implements ServletContextListener {
    private final static Logger logger = LoggerFactory.getLogger(DBInitListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        dBInit();
        logger.info("listener init");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("listener destroy");

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
            logger.info("create table success");
            String insertSql1 = "insert into user_info (name) values('sean')";
            String insertSql2 = "insert into user_info (name) values('sean')";
            stmt.execute(insertSql1);
            logger.info("insert user sean success");
            stmt.execute(insertSql2);
            logger.info("insert user sean success");
        } catch (SQLException e) {
            logger.error("DBInit error", e);
        } finally {
            DBUtils.close(stmt);
            DBUtils.close(connection);
        }
    }
}
