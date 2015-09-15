package com.sean.servlet.utils;

import com.google.common.io.Closer;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-16 Time: 下午3:33
 */
public class DBUtils {
    private final static Logger logger = LoggerFactory.getLogger(DBUtils.class);

    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        Properties properties = getDBProperties("jdbc.properties");
        try {

            Class.forName(properties.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
        } catch (ClassNotFoundException e) {
            logger.warn("Class not found Exception{}", e);
        }
        return connection;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn("Close connection exception! {}", e);
            }
        }
    }

    public static void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.warn("Close preparedStatement exception! {}", e);
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("Close resultSet exception! {}", e);
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warn("Close Statement exception! {}", e);
            }
        }
    }

    private static Properties getDBProperties(String properties) {
        Closer closer = Closer.create();
        Properties pro = new Properties();
        try {
            URL url = Resources.getResource(properties);
            BufferedReader br = closer.register(new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")));
            pro.load(br);
        } catch (IOException e) {
            logger.error("get db.properties error", e);
        }
        return pro;
    }

}
