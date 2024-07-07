package com.example.ftechdevice.Model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private int id;
    private int status;
    private String title;
    private String description;
    private List<OrderDetailModel> orderDetailList;

    public OrderModel(int id, int status, String title, String description, List<OrderDetailModel> orderDetailList) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.orderDetailList = orderDetailList;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<OrderDetailModel> getOrderDetailList() {
        return orderDetailList;
    }
}
