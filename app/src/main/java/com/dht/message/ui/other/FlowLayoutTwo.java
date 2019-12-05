package com.dht.message.ui.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dht.message.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Administrator on 2018/12/14 15:05
 */
public class FlowLayoutTwo extends ViewGroup {

    private static final String TAG = "FlowLayoutTwo";
    private List<List<View>> viewLists = new ArrayList<>();
    private List<Integer> numList = new ArrayList<>();

    private final static int defaultSize = 200;

    public FlowLayoutTwo(Context context) {
        super(context);
    }

    public FlowLayoutTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayoutTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        Log.d(TAG, "********************************************onMeasure: count = " + count + "********************************************");
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int width = childWidth + params.leftMargin + params.rightMargin + getPaddingLeft() + getPaddingRight();
            int height = childHeight + params.topMargin + params.bottomMargin + getPaddingTop() + getPaddingBottom();
            Log.d(TAG, "onMeasure: width = " + width + ", childWidth = " + childWidth + ", leftMargin = " + params.leftMargin + ", rightMargin = " + params.rightMargin
                    + ", getPaddingLeft = " + getPaddingLeft() + ", getPaddingRight = " + getPaddingRight());
            Log.d(TAG, "onMeasure: height = " + height + ", childHeight = " + childHeight + ", topMargin = " + params.topMargin + ", bottomMargin = " + params.bottomMargin
                    + ", getPaddingTop = " + getPaddingTop() + ", getPaddingBottom = " + getPaddingBottom());

            int widthSize = measureSize(childWidth, widthMeasureSpec);
            int heightSize = measureSize(childHeight, heightMeasureSpec);

            setMeasuredDimension(widthSize, heightSize);
        }

    }

    private int lineWidth = 10;
    private int lineHeight = 10;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout() called with: changed = [" + changed + "], l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        int count = getChildCount();
        int totalWidth = 0;
        @SuppressLint("DrawAllocation")
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            totalWidth += childWidth + params.leftMargin + params.rightMargin + getPaddingLeft() + getPaddingRight() + lineWidth;
            if (totalWidth > ScreenUtil.getWidth()) {
                viewLists.add(viewList);
                totalWidth = 0;
                viewList.clear();
            }
            viewList.add(child);
            if (i == count - 1 && totalWidth < ScreenUtil.getWidth()) {
                viewLists.add(viewList);
            }

//            int width = childWidth + params.leftMargin + params.rightMargin + getPaddingLeft() + getPaddingRight();
//            int height = childHeight + params.topMargin + params.bottomMargin + getPaddingTop() + getPaddingBottom();
//            Log.d(TAG, "onLayout: width = " + width + ", childWidth = " + childWidth + ", leftMargin = " + params.leftMargin + ", rightMargin = " + params.rightMargin
//                    + ", getPaddingLeft = " + getPaddingLeft() + ", getPaddingRight = " + getPaddingRight());
//            Log.d(TAG, "onLayout: height = " + height + ", childHeight = " + childHeight + ", topMargin = " + params.topMargin + ", bottomMargin = " + params.bottomMargin
//                    + ", getPaddingTop = " + getPaddingTop() + ", getPaddingBottom = " + getPaddingBottom());

        }
        int top = lineHeight;
        int bottom = lineHeight;
        Log.d(TAG, "onLayout: viewLists = " + viewLists);
        Log.d(TAG, "onLayout: viewLists size= " + viewLists.size());
        for (int i = 0; i < viewLists.size(); i++) {

            @SuppressLint("DrawAllocation")
            List<View> list = new ArrayList<>(viewLists.get(i));
            Log.d(TAG, "onLayout: list.size = " + list.size() + ", i = " + i);
            View view = list.get(0);

            int left = 0;
            int right = 0;
            bottom += view.getMeasuredHeight();
            for (int j = 0; j < list.size(); j++) {
                View child = list.get(j);
                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                right += childWidth + params.leftMargin + params.rightMargin + getPaddingLeft() + getPaddingRight();
                Log.d(TAG, "onLayout: left = " + left + ", top = " + top + ", right = " + right + ", bottom = " + bottom);
                child.layout(left, top, right, bottom);
                left = right + lineWidth;

            }
            top = bottom + lineHeight;
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

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
