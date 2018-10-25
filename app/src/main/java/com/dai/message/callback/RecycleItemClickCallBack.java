package com.dai.message.callback;


import com.dai.message.callback.listener.ItemClickListener;

/**
 * 实现RecyclerView Item回调
 * <p>
 * created by dht on 2018/7/2 15:26
 */
public class RecycleItemClickCallBack<T> implements ItemClickListener<T> {

    @Override
    public void onItemClickListener(int type, T value, int position) {

    }

    @Override
    public void onItemClickListener(boolean isSelected, T value, int position) {

    }

    @Override
    public void onItemLongClickListener(int type, T value, int position) {

    }

    @Override
    public void onItemClickListener(T value, int position) {

    }

    @Override
    public void onItemLongClickListener(T value, int position) {

    }
}
