package com.dht.baselib.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * GridLayoutManager 分割线
 * <p>
 * created by dht on 2018/7/4 10:30
 */
public class HorizontalDecoration extends RecyclerView.ItemDecoration {

    private int height;

    public HorizontalDecoration(int height) {
        this.height = height;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = height;
        outRect.top = height;
    }
}
