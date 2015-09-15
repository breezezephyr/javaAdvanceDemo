package com.sean.fresh;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-15 Time: 上午11:13
 */
public class User {
    private Integer id;
    private String name;
    private String phone;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + '\'' + ", phone='" + phone + '\'';
    }
}
