package com.example.ftechdevice.Model;

import com.google.gson.annotations.SerializedName;

public class UserJWT {

    public UserJWT() {
    }

    public UserJWT(String accessToken, String subject, String email, int userId, String roleName, String phone, long issuedAt, long expiration) {
        this.accessToken = accessToken;
        this.subject = subject;
        this.email = email;
        this.userId = userId;
        this.roleName = roleName;
        this.phone = phone;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @SerializedName("sub")
    private String subject;

    @SerializedName("email")
    private String email;

    @SerializedName("userId")
    private int userId;

    @SerializedName("RoleName")
    private String roleName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("iat")
    private long issuedAt;

    @SerializedName("exp")
    private long expiration;

    // Getters and Setters

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}