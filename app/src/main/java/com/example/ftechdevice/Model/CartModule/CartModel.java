package com.example.ftechdevice.Model.CartModule;

import android.os.Parcel;
import android.os.Parcelable;

public class CartModel implements Parcelable {
    private final int id;
    private CartResponse.Product product;
    private int quantity;
    private CartResponse.User user;
    private static int idCounter = 0;

    private CartModel(int id, CartResponse.Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }


    protected CartModel(Parcel in) {
        id = in.readInt();
        product = in.readParcelable(CartResponse.Product.class.getClassLoader());
        quantity = in.readInt();
        user = in.readParcelable(CartResponse.User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(product, flags);
        dest.writeInt(quantity);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    // Getters and Setters
    public CartResponse.Product getProduct() {
        return product;
    }

    public static CartModel create(CartResponse.Product product, int quantity) {
        return new CartModel(idCounter++, product, quantity);
    }

    public static CartModel create(int id, CartResponse.Product product, int quantity) {
        return new CartModel(id, product, quantity);
    }



    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartResponse.User getUser() {
        return user;
    }

    public void setUser(CartResponse.User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", user=" + user +
                '}';
    }

}