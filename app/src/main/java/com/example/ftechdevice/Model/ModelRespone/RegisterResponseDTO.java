package com.example.ftechdevice.Model.ModelRespone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponseDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("role")
    private Role role;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("authorities")
    private List<Authority> authorities;

    @SerializedName("accountNonExpired")
    private boolean accountNonExpired;

    @SerializedName("accountNonLocked")
    private boolean accountNonLocked;

    @SerializedName("credentialsNonExpired")
    private boolean credentialsNonExpired;

    // Inner classes for Role and Authority
    public static class Role {
        private int id;
        private Boolean isActive;
        private String name;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Authority {
        private String authority;

        // Getters and Setters
        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }
}
