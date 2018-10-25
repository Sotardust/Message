package com.dai.message.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.dai.message.callback.RecycleItemClickCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础RecyclerView适配器
 * <p>
 * created by dht on 2018/7/2 15:20
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mList = new ArrayList<>();

    @NonNull
    protected RecycleItemClickCallBack callBack;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateVH(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindVH(holder, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 初始化数据，数据变更
     *
     * @param mList 数据源
     */
    public abstract void setChangeList(List<T> mList);

    public abstract RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType);

    public abstract void onBindVH(@NonNull RecyclerView.ViewHolder holder, int position);

}
