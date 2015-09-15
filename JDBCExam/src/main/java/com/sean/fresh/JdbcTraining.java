package com.sean.fresh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-15 Time: 上午11:12
 */
public class JdbcTraining {
    private final static Logger logger = LoggerFactory.getLogger(JdbcTraining.class);

    public static void main(String[] args) {

        String createSQL = "create table user_info(" + "id bigint(20) unsigned NOT NULL AUTO_INCREMENT,"
                + "name varchar(100) not null," + "phone varchar(11)," + "primary key(id)" + ");";
        String insertSQL = "insert into user_info(name, phone) values(?,?)";
        String updateSQL = "update user_info set phone = ? where name = ?";
        String selectSQL = "select * from user_info";
        String deleteSQL = "delete from user_info where name = ?";

        Connection connection = null;
        PreparedStatement pst = null;
        PreparedStatement selectPst = null;
        try {
            connection = DBUtils.getConnection();
            pst = connection.prepareStatement(createSQL);
            pst.execute();
            logger.info("Create table finished\n");

            logger.info("step1.新增两条数据");
            pst = connection.prepareStatement(insertSQL);
            pst.setString(1, "tom");
            pst.setString(2, "186");
            pst.executeUpdate();
            logger.info("insert user {}", "tom");

            pst.setString(1, "jim");
            pst.setString(2, "189");
            pst.executeUpdate();
            logger.info("insert user {}\n", "jim");

            logger.info("step2.遍历所有结果");
            selectPst = connection.prepareStatement(selectSQL);
            selectAll(selectPst);

            pst = connection.prepareStatement(deleteSQL);
            pst.setString(1, "jim");
            pst.executeUpdate();
            logger.info("step3.删除name={}的数据\n", "jim");

            logger.info("step4.遍历所有结果");
            selectAll(selectPst);

            pst = connection.prepareStatement(updateSQL);
            pst.setString(1, "110");
            pst.setString(2, "tom");
            pst.executeUpdate();
            logger.info("step5.修改name={}的电话号码为{}\n", "tom", "110");

            logger.info("step6.遍历所有结果");
            selectAll(selectPst);
        } catch (SQLException e) {
            logger.warn("SQL Exception{}", e);
        } finally {
            DBUtils.close(pst);
            DBUtils.close(selectPst);
            DBUtils.close(connection);
        }
    }

    public static void selectAll(PreparedStatement pst) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPhone(resultSet.getString("phone"));
                logger.info("UserInfo:{}", user);
            }
            logger.info("");
        } finally {
            DBUtils.close(resultSet);
        }

    }
}
