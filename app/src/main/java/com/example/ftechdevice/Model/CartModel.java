package com.example.ftechdevice.Model;

public class CartModel {
    private final int id;
    private final ToyModel toyModel;
    private int quantity;
    private static int idCounter = 0;

    private CartModel(int id, ToyModel toyModel, int quantity) {
        this.id = id;
        this.toyModel = toyModel;
        this.quantity = quantity;
    }







    // test asdasdadsads
    public static CartModel create(ToyModel toyModel, int quantity) {
        return new CartModel(idCounter++, toyModel, quantity);
    }

    public int getId() {
        return id;
    }

    public ToyModel getToyModel() {
        return toyModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}