package com.sean.mobile.training.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LogIntercepter {
    @Before("execution(public * com.sean.mobile.training.dao.impl.*.saveUser(*))")
    public void before() {
        System.out.println("Execution at " + new Date());
    }
}
