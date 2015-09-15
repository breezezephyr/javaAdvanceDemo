package com.sean.mobile.training.aop;

import java.util.Date;

/**
 * Advice引介的对象 Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-19 Time: 下午8:51
 */
public class Logger {
    public void before() {
        System.out.println("Logger enabled!  Start at " + new Date());
    }

    private void after() {
        System.out.println("end at " + new Date());
    }
}
