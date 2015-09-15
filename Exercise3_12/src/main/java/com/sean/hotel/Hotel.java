package com.sean.hotel;

import java.util.Map;

/**
 * 构造Hotel类 Author: xiappeng.cai Date: 14-4-29 Time: 上午11:42
 */
public class Hotel {
    private String name;
    private String address;
    private int price;
    private Map<String, String> properties;

    public Hotel(String name, String address, int price) {
        this.name = name;
        this.address = address;
        this.price = price;
    }

    public Hotel() {
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Hotel{" + "name='" + name + '\'' + ", address='" + address + '\'' + ", price=" + price
                + ", properties=" + properties + '}';
    }
}
