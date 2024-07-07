package com.example.ftechdevice.Model;

import java.io.Serializable;

public class OrderDetailModel implements Serializable {
    private int id;
    private int quantity;
    private String price;
    private ProductModel product;
    private int status;

    public OrderDetailModel(int id, int quantity, String price, ProductModel product, int status) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public ProductModel getProduct() {
        return product;
    }

    public int getStatus() {
        return status;
    }
}
