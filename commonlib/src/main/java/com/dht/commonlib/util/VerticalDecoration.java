package com.dht.commonlib.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * LinearLayoutManager 分割线
 * <p>
 * created by dht on 2018/7/3 09:41
 */
public class VerticalDecoration extends RecyclerView.ItemDecoration {

    private int height;

    public VerticalDecoration(int height) {
        this.height = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = height;
    }
}
