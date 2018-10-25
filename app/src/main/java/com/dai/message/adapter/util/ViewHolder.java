package com.dai.message.adapter.util;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 *
 * created by dht on 2018/7/4 09:33
 */
public class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final T mBinding;

    public ViewHolder(T mBinding) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
    }

}
