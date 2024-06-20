package com.example.ftechdevice.Model.ModelRespone;

import com.example.ftechdevice.Model.ProductModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductReponse {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private int price;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("isActive")
    private Boolean isActive;
    @SerializedName("content")
    private List<ProductModel> content;

    public List<ProductModel> getContent() {
        return content;
    }

    public void setContent(List<ProductModel> content) {
        this.content = content;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    @SerializedName("productCategory")
    private int productCategory;

    public ProductReponse(int id, String name, String description, int price, int quantity, String imageUrl, Boolean isActive, int productCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.productCategory = productCategory;
    }
}
