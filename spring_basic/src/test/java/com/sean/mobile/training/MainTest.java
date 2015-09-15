package com.sean.mobile.training;

import com.sean.mobile.training.bean.User;
import com.sean.mobile.training.ioc.BeanFactory;
import com.sean.mobile.training.ioc.impl.ClassPathXmlApplicationContext;
import com.sean.mobile.training.service.IUserService;
import com.sean.mobile.training.service.UserService;

/**
 * 测试模拟Spring Ioc容器，对UserService的实例，将UserService addUser()作为切点，通过动态代理模仿Spring Aop对addUser增加前置advice Logger功能。
 * 
 * @author xiaopeng.cai
 */
public class MainTest {
    public static void main(String[] args) {
        User user = new User();
        user.setName("hanmeimei");

        BeanFactory beanFactory = new ClassPathXmlApplicationContext("beans.xml");
        IUserService service = (IUserService) beanFactory.getBean("userservice");
        IUserService userService = beanFactory.getBean(UserService.class);
        service.addUser(user);
        userService.addUser(user);
    }
}
