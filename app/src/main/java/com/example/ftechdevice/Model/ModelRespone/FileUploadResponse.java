package com.example.ftechdevice.Model.ModelRespone;

import com.google.gson.annotations.SerializedName;

public class FileUploadResponse {
    @SerializedName("url")
    private String url;

    public FileUploadResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
