package com.sean.servlet.model;

public class User {
    private Integer id;
    private String username;

    public User() {
    }

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean equals(User o) {
        return id.equals(o.getId()) && username.equals(o.getUsername());
    }

    @Override
    public String toString() {
        return "UserInfo: " + "id=" + id + ", username='" + username;
    }
}
