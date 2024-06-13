package com.example.ftechdevice.AppConfig.CustomView.CustomBottomNav;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BottomBarItem {
    private String title;
    private final Drawable icon;
    private RectF rect;
    private float badgeSize;

    public BottomBarItem(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
        this.rect = new RectF();
        this.badgeSize = 0f;
    }

    public BottomBarItem(String title, Drawable icon, RectF rect, float badgeSize) {
        this.title = title;
        this.icon = icon;
        this.rect = rect;
        this.badgeSize = badgeSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public float getBadgeSize() {
        return badgeSize;
    }

    public void setBadgeSize(float badgeSize) {
        this.badgeSize = badgeSize;
    }
}
