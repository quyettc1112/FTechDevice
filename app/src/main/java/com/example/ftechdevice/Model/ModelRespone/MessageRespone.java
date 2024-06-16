package com.example.ftechdevice.Model.ModelRespone;

import com.google.gson.annotations.SerializedName;

public class MessageRespone {

    @SerializedName("message")
    private String message;

    public MessageRespone(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}