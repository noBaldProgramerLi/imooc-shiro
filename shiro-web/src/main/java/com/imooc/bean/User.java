package com.imooc.bean;

public class User {
    private String username;
    private String password;
    private Boolean remeberMe;

    public Boolean getRemeberMe() {
        return remeberMe;
    }

    public void setRemeberMe(Boolean remeberMe) {
        this.remeberMe = remeberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
