package com.dht.message.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.dht.message.R;
import com.dht.message.adapter.util.ViewHolder;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.message.databinding.RecycleItemAllCallsBinding;
import com.dht.message.repository.entity.AllCallsEntity;
import com.dht.baselib.base.BaseAdapter;
import java.util.List;

/**
 * 展示通话记录列表适配器
 * <p>
 * created by dht on 2018/7/3 18:28
 */
public class CallRecordAdapter extends BaseAdapter<AllCallsEntity> {

    private static final String TAG = "CallRecordAdapter";

    public CallRecordAdapter(@NonNull RecycleItemClickCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public void setChangeList(List<AllCallsEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemAllCallsBinding mBinding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.recycle_item_all_calls,
                        parent, false);
        mBinding.setCallBack(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder<RecycleItemAllCallsBinding>) holder).mBinding.setAllCallsEntity(mList.get(position));
        ((ViewHolder<RecycleItemAllCallsBinding>) holder).mBinding.setIndex(position);
        ((ViewHolder<RecycleItemAllCallsBinding>) holder).mBinding.executePendingBindings();
    }
}
