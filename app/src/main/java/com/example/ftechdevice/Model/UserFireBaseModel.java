package com.example.ftechdevice.Model;

public class UserFireBaseModel {

    public String FCMToken;
    public String email;
    public String name;
    public String password;
    public int roleid;

    public UserFireBaseModel(String FCMToken, String email, String name, String password, int roleid) {
        this.FCMToken = FCMToken;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roleid = roleid;
    }

    public UserFireBaseModel() {
    }
}
