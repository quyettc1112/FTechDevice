package com.example.ftechdevice.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderModel{
    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private int status;

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;

    @SerializedName("orderDetailList")
    private List<OrderDetailModel> orderDetailList;

    @SerializedName("userId")
    private int userId;

    public OrderModel(int id, int status, String title, String description, List<OrderDetailModel> orderDetailList) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.orderDetailList = orderDetailList;
    }
    public OrderModel(String title, String description, int userId, int status, List<OrderDetailModel> orderDetailList) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.status = status;
        this.orderDetailList = orderDetailList;
    }
    public OrderModel(int id, String title, String description, int userId, int status, List<OrderDetailModel> orderDetails) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.status = status;
        this.orderDetailList = orderDetails;
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
    class ProductModel {
        private int id;
        private String name;
        private String description;
        private int price;
        private int quantity;
        private String imageUrl;
        private int categoryId;

        public ProductModel(int id, String name, String description, int price, int quantity, String imageUrl, int categoryId) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
            this.categoryId = categoryId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }
}
