package com.example.ftechdevice.Model.ModelRequestDTO;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestDTO {

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("roleId")
    private Integer roleId;

    @SerializedName("avatar")
    private String avatar;

    // Constructor mặc định
    public RegisterRequestDTO() {
        // Khởi tạo các trường với giá trị mặc định
        this.email = "";
        this.username = "";
        this.password = "";
        this.fullName = "";
        this.phone = "";
        this.address = "";
        this.roleId = 0;
        this.avatar = "";
    }

    // Constructor đầy đủ
    public RegisterRequestDTO(String email, String username, String password, String fullName, String phone, String address, Integer roleId, String avatar) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.avatar = avatar;
    }

    // Getters và setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}