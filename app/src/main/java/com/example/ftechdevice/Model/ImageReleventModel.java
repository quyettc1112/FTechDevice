package com.example.ftechdevice.Model;

public class ImageReleventModel {
    private final int id;
    private final int image;

    public ImageReleventModel(int id, int image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }
}