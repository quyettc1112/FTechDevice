package com.example.ftechdevice.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ProductModel implements Serializable{
    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private String imageUrl;
    private Boolean isActive;
    private ProductCategory productCategory;

    public ProductModel(int id, String name, String description, int price, int quantity, String imageUrl, Boolean isActive, ProductCategory productCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.productCategory = productCategory;
    }

    public ProductModel() {
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public static class ProductCategory implements Parcelable {
        private int id;
        private String name;
        private Boolean isActive;

        public ProductCategory(int id, String name, Boolean isActive) {
            this.id = id;
            this.name = name;
            this.isActive = isActive;
        }

        protected ProductCategory(Parcel in) {
            id = in.readInt();
            name = in.readString();
            byte tmpIsActive = in.readByte();
            isActive = tmpIsActive == 0 ? null : tmpIsActive == 1;
        }

        public static final Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {
            @Override
            public ProductCategory createFromParcel(Parcel in) {
                return new ProductCategory(in);
            }

            @Override
            public ProductCategory[] newArray(int size) {
                return new ProductCategory[size];
            }
        };

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

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeByte((byte) (isActive == null ? 0 : isActive ? 1 : 2));
        }
    }
}
