package com.sean.mobile.training.aop;

import com.sean.mobile.training.utils.AopUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-19 Time: 下午8:51
 */
public class AopHandler implements InvocationHandler {
    private Object target;
    private String methodName;
    private String aspectRef;
    private String advisorMethod;
    Map<String, Object> nameMap;

    public AopHandler(Object bean, String methodName, String aspectRef, String advisorMethod,
            Map<String, Object> nameMap) {
        this.target = bean;
        this.methodName = methodName;
        this.aspectRef = aspectRef;
        this.advisorMethod = advisorMethod;
        this.nameMap = nameMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 只考虑了一种情况 aop:before
        if (method.getName().equals(methodName)) {
            Object refBean = nameMap.get(aspectRef);
            if (refBean != null) {
                Method before = refBean.getClass().getMethod(advisorMethod);
                before.invoke(refBean);
            }
        }
        return method.invoke(target, args);
    }
}
