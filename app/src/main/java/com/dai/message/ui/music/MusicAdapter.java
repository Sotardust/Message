package com.dai.message.ui.music;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dai.message.base.BaseAdapter;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemMusicBinding;

import java.util.List;

/**
 * created by Administrator on 2018/12/27 14:53
 */
public class MusicAdapter extends BaseAdapter<String> {

    MusicAdapter(RecycleItemClickCallBack<String> recycleItemClickCallBack) {
        this.callBack = recycleItemClickCallBack;
    }

    private int[] images = {R.mipmap.icon_music_orange_64, R.mipmap.icon_music_orange_64,
            R.mipmap.icon_music_orange_64, R.mipmap.icon_music_orange_64, R.mipmap.icon_music_orange_64};

    @Override
    public void setChangeList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemMusicBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycle_item_music, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressLint("DefaultLocale")
    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicImage.setImageResource(images[position]);
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicContent.setText(mList.get(position));
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicNumber.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicNumber.setText(String.format("(%d)", position));
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.setIndex(position);

    }
}