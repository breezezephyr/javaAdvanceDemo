package com.sean.mobile.training;

import com.sean.mobile.training.bean.User;
import com.sean.mobile.training.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    public static void main(String[] args) {
        User user = new User();
        user.setName("hanmeimei");

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"services.xml"});

        UserService userService = (UserService) context.getBean("userService");
        userService.addUser(user);
    }
}
