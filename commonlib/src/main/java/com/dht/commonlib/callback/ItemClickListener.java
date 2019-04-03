package com.dht.commonlib.callback;

/**
 * RecycleView Item点击事件回调接口
 * <p>
 * created by dht on 2018/7/2 15:28
 */
public interface ItemClickListener<T> {

    void onItemClickListener(int type, T value, int position);

    void onItemClickListener(boolean isSelected, T value, int position);

    void onItemLongClickListener(int type, T value, int position);

    void onItemClickListener(T value, int position);

    void onItemLongClickListener(T value, int position);
}
