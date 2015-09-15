package com.sean.mobile.training.ioc;

public interface BeanFactory {
    public Object getBean(String name);

    public <T> T getBean(Class<T> clazz);
}
