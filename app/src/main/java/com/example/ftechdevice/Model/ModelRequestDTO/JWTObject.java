package com.example.ftechdevice.Model.ModelRequestDTO;

import com.google.gson.annotations.SerializedName;

public class JWTObject {

    @SerializedName("jwtToken")
    private String jwtToken;

    public JWTObject(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}