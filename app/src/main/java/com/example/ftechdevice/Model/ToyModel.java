package com.example.ftechdevice.Model;

import java.util.List;

public class ToyModel {

    private final int id;
    private final String toyName;
    private final double toyPrice;
    private final int toyImage;
    private final double toyRating;
    private final Boolean isLike;
    private final String toyDescription;
    private final int typePoppular;
    private final List<Integer> listImage;
    private final CategoryModel categoryModel;
    private final SellerInfoModel sellerInfo;
    private final String gender;

    public ToyModel(int id, String toyName, int toyImage,String toyDescription,double toyRating,  Boolean isLike,  double toyPrice, int typePoppular, CategoryModel categoryModel, SellerInfoModel sellerInfo, List<Integer> listImage, String gender) {
        this.id = id;
        this.toyName = toyName;
        this.toyPrice = toyPrice;
        this.toyImage = toyImage;
        this.toyRating = toyRating;
        this.isLike = isLike;
        this.toyDescription = toyDescription;
        this.typePoppular = typePoppular;
        this.listImage = listImage;
        this.categoryModel = categoryModel;
        this.sellerInfo = sellerInfo;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getToyName() {
        return toyName;
    }

    public double getToyPrice() {
        return toyPrice;
    }

    public int getToyImage() {
        return toyImage;
    }

    public double getToyRating() {
        return toyRating;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public String getToyDescription() {
        return toyDescription;
    }

    public int getTypePoppular() {
        return typePoppular;
    }

    public List<Integer> getListImage() {
        return listImage;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public SellerInfoModel getSellerInfo() {
        return sellerInfo;
    }

    public String getGender() {
        return gender;
    }

    public static class CategoryModel {
        private final int id;
        private final String name;
        private final int image;

        public CategoryModel(int id, String name, int image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getImage() {
            return image;
        }
    }

    public static class SellerInfoModel {
        private final int id;
        private final String name;
        private final String email;
        private final String phone;

        public SellerInfoModel(int id, String name, String email, String phone) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
    }
}
