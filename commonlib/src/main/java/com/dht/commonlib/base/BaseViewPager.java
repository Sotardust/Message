package com.dht.commonlib.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class BaseViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";

    public BaseViewPager(@NonNull Context context) {
        super(context);
    }

    public BaseViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int result = (int) Math.abs(ev.getX() - 1080.f);
        return (result >= 50) && super.onInterceptTouchEvent(ev);
    }
}
