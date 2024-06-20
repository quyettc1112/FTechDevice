package com.example.ftechdevice.Model;

public class ProductModel {
    private final int id;
    private final String name;
    private final String description;
    private final int price;
    private final int quantity;
    private final String imageUrl;
    private final Boolean isActive;

    public ProductModel(int id, String name, String description, int price, int quantity, String imageUrl, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getActive() {
        return isActive;
    }

    public static class ProductCategory {
        private final int id;
        private final String name;
        private final Boolean isActive;

        public ProductCategory(int id, String name, Boolean isActive) {
            this.id = id;
            this.name = name;
            this.isActive = isActive;
        }
    }
}
