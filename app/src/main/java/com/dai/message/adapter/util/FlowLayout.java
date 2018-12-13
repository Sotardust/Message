package com.dai.message.adapter.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局
 * <p>
 * created by dht on 2018/12/13 17:36
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        set
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int defaultSize = 200;

    int lineWidth = 10;
    int lineHeight = 5;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        Log.d(TAG, "onMeasure: childCount = " + childCount);

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams


            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
//            Log.d(TAG, "onMeasure: viewWidth = " + viewWidth +
//                    ", viewMeasuredWidth" + viewMeasuredWidth +
//                    ", viewHeight" + viewHeight +
//                    ", viewMeasuredHeight" + viewMeasuredHeight);

            Log.d(TAG, "onMeasure: childWidth = " + childWidth + ", childHeight" + childHeight);
            int width = measureSize(childWidth, heightMeasureSpec);
            int height = measureSize(childHeight, widthMeasureSpec);

            Log.d(TAG, "onMeasure: height = " + height + " , width =" + width);
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {


        Log.d(TAG, "onLayout() called with: b = [" + b + "], i = [" + i + "], i1 = [" + i1 + "], i2 = [" + i2 + "], i3 = [" + i3 + "]");
        int left = 0;
        int top = 10;
        int right = 0;
        int bottom = 10;
        int childCount = getChildCount();
        for (int j = 0; j < childCount; j++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            left += childWidth;
            Log.d(TAG, "onLayout: childWidth = " + childWidth + ", childHeight" + childHeight);

//            child.layout(left, 100, right+childWidth, 100);
//            child.layout(i+i*10, i1+i*10, i2+i*10 , i3+i*10);
            child.layout(100, 100   , 100, 100);

        }

    }

    /**
     * 测量、设置View默认宽高
     *
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    public int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }
}
