package com.uas.jdbc.entity;

/**
 * Created by Pro1 on 2018/3/6.
 */
public class User {
    private long id;
    private String name;
    private String email;
    private String mobile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static User randomUser() {
        User user = new User();
        user.setName(String.valueOf(Math.random()));
        user.setEmail(user.getName() + "@test.com");
        return user;
    }
}
