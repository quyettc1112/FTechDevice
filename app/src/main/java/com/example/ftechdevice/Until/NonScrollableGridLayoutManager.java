package com.example.ftechdevice.Until;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

public class NonScrollableGridLayoutManager extends GridLayoutManager {

    public NonScrollableGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        // Always return false to prevent vertical scrolling
        return false;
    }
}