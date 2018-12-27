package com.dai.message.ui.music.cloud;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dai.message.base.BaseAdapter;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemLocalBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Administrator on 2018/12/27 16:37
 */
public class CloudDiskAdapter extends BaseAdapter<String> {

    private ArrayList<String> usernameList = new ArrayList<>();

    CloudDiskAdapter(RecycleItemClickCallBack<String> recycleItemClickCallBack) {
        this.callBack = recycleItemClickCallBack;
    }

    @Override
    public void setChangeList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemLocalBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycle_item_cloud_disk, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder<RecycleItemLocalBinding>) holder).mBinding.tvSongName.setText(mList.get(position));
            ((ViewHolder<RecycleItemLocalBinding>) holder).mBinding.tvUsername.setText(usernameList.get(position));
            ((ViewHolder<RecycleItemLocalBinding>) holder).mBinding.setName(mList.get(position));
            ((ViewHolder<RecycleItemLocalBinding>) holder).mBinding.setIndex(position);
        }
    }

    void setUsernameList(ArrayList<String> usernameList) {
        this.usernameList = usernameList;
    }

}
