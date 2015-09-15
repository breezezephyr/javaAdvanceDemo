package com.sean.fresh;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * User: liuzz
 * Date: 13-11-2
 * Time: 下午11:53
 */
@XmlRootElement
public class User {

    private String name;

    private int age;

    private Date birth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", birth=" + birth +
          '}';
    }
}
