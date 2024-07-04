package com.example.ftechdevice.Model.ModelRespone;

import com.example.ftechdevice.Model.OrderModel;

import java.util.List;

public class OrderResponse {
    private List<OrderModel> content;

    public List<OrderModel> getContent() {
        return content;
    }

    public void setContent(List<OrderModel> content) {
        this.content = content;
    }
}
