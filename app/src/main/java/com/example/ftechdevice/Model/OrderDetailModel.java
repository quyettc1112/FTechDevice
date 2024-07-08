package com.example.ftechdevice.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetailModel{
   @SerializedName("id")
    private int id;
    @SerializedName("quantity")
   private int quantity;
    @SerializedName("price")
    private String price;
    @SerializedName("product")
    private ProductModel product;

    @SerializedName("status")
    private int status;

    public OrderDetailModel(int id, int quantity, String price, ProductModel product, int status) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.status = status;
    }
    public OrderDetailModel(int quantity, String price, ProductModel product, int status) {
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
