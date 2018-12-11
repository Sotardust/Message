package com.dai.message.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dai.message.base.BaseAdapter;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemLinkageBinding;
import com.dai.message.databinding.RecycleItemNextLinkageBinding;

import java.util.List;

/**
 * created by Administrator on 2018/12/7 11:23
 */
public class NextLinkageAdapter extends BaseAdapter<String> {


    private boolean isShowAll = true;

    public NextLinkageAdapter(@NonNull RecycleItemClickCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setChangeList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemNextLinkageBinding mBinding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.recycle_item_next_linkage,
                        parent, false);
        mBinding.setCallBack(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.content.setText(mList.get(position));
            ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.content.setText(mList.get(position));
            ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.content.setVisibility(isShowAll ? View.VISIBLE : (position > 2 ? View.GONE : View.VISIBLE));
            ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.setValue(mList.get(position));
            ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.setIndex(position);
            ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.content.setOnClickListener(new View.OnClickListener() {
                boolean isClick = false;
                @Override
                public void onClick(View view) {
                    Log.d("dht", "NextLinkageAdapter onClick: ");
                    callBack.onItemClickListener(mList.get(position), position);
                    ((ViewHolder<RecycleItemNextLinkageBinding>) holder).mBinding.content.setBackgroundResource(isClick ? R.drawable.bound_recycle_item : R.drawable.bound_recycle_item_blue);
                    isClick = !isClick;
                }
            });
        }
    }


    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
        notifyDataSetChanged();
    }

}
