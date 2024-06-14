package com.example.ftechdevice.Until;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BottomMarginItemDecoration extends RecyclerView.ItemDecoration {

    private final int bottomMargin;

    public BottomMarginItemDecoration(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = bottomMargin;
    }
}