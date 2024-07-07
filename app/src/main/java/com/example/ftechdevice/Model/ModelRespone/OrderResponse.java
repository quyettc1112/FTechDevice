package com.example.ftechdevice.Model.ModelRespone;

import com.example.ftechdevice.Model.OrderModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

    @SerializedName("content")
    private List<OrderModel> content;

    public List<OrderModel> getContent() {
        return content;
    }

    public void setContent(List<OrderModel> content) {
        this.content = content;
    }
}
