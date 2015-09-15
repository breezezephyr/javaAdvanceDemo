package com.sean.mobile.training.aop;

import com.sean.mobile.training.dao.UserDao;

  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.util.Date;

public class LogHandler implements InvocationHandler {
    private UserDao target;

    public LogHandler(UserDao target) {
        this.target = target;
    }

    private void before(Method method) {
        System.out.println("Execution " + method.getName() + " at " + new Date());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        before(method);
        return method.invoke(target, args);
    }
}
