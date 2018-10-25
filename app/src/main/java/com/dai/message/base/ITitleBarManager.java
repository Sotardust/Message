package com.dai.message.base;

import android.view.View;


/**
 * 处理TitleBar显示、点击等操作
 * created by dht on 2018/7/3 13:25
 */
public interface ITitleBarManager extends View.OnClickListener {

    /**
     * 初始化TitleBar视图View
     *
     * @param view view
     */
    void initViews(View view);

    /**
     * 处理点击事件
     */
    void handlingClickEvents(View view);

    /**
     * 设置TitleBar视图控件可见或设置文本内容
     */
    void setTitleBarVisibilityOrText();

}
