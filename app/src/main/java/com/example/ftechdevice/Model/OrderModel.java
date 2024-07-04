package com.example.ftechdevice.Model;

public class OrderModel {
    private int id;
    private int status;
    private String title;
    private String description;
    private int userId;

    public OrderModel(int id, int status, String title, String description, int userId) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }
}

