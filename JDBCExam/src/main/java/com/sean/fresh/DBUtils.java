package com.sean.fresh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-15 Time: 下午1:58
 */
public class DBUtils {
    private final static Logger logger = LoggerFactory.getLogger(DBUtils.class);

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(ConnProperties.ClASSNAME);
            connection = DriverManager.getConnection(ConnProperties.URL, ConnProperties.USERNAME,
                    ConnProperties.PASSWORD);
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
                logger.warn("Connection exception! {}", e);
            }
        }
    }

    public static void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.warn("PreparedStatement exception! {}", e);
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("ResultSet exception! {}", e);
            }
        }
    }
}
