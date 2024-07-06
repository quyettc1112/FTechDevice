package com.example.ftechdevice.Model;

import com.example.ftechdevice.Model.CartModule.CartResponse;

public class CartModel {
    private final int id;
    private final CartResponse.Product product;
    private int quantity;
    private static int idCounter = 0;

    private CartModel(int id, CartResponse.Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }


    public CartResponse.Product getProduct() {
        return product;
    }

    // test asdasdadsads
    public static CartModel create(CartResponse.Product product, int quantity) {
        return new CartModel(idCounter++, product, quantity);
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
}