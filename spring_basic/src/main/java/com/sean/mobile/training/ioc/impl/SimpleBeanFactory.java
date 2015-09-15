package com.sean.mobile.training.ioc.impl;

import com.sean.mobile.training.dao.impl.DiskUserDao;
import com.sean.mobile.training.dao.impl.MysqlUserDao;
import com.sean.mobile.training.dao.impl.OracleUserDao;
import com.sean.mobile.training.ioc.BeanFactory;

import java.util.HashMap;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {
    private static Map<String, Object> map = new HashMap<String, Object>();
    private static Map<Class, Object> classMap = new HashMap<Class, Object>();

    public SimpleBeanFactory() {
        DiskUserDao diskUserDao = new DiskUserDao();
        MysqlUserDao mysqlUserDao = new MysqlUserDao();
        OracleUserDao oracleUserDao = new OracleUserDao();

        map.put("disk", diskUserDao);
        map.put("mysql", mysqlUserDao);
        map.put("oracle", oracleUserDao);

        classMap.put(DiskUserDao.class, diskUserDao);
        classMap.put(MysqlUserDao.class, mysqlUserDao);
        classMap.put(OracleUserDao.class, oracleUserDao);
    }

    @Override
    public Object getBean(String name) {
        return map.get(name);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T)classMap.get(clazz);
    }
}
