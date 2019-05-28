package com.dai.message.ui.music;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dht.baselib.base.BaseAdapter;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemMusicBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Administrator on 2018/12/27 14:53
 *
 * @author Administrator
 */
public class MusicAdapter extends BaseAdapter<String> {

    private List<Integer> endList = new ArrayList<>();

    MusicAdapter (RecycleItemClickCallBack<String> recycleItemClickCallBack) {
        this.callBack = recycleItemClickCallBack;
    }

    private int[] images = {R.mipmap.icon_cherry_original_64, R.mipmap.icon_dragonfruit_original_64,
            R.mipmap.icon_egg_original_64, R.mipmap.icon_goods_original_64, R.mipmap.icon_grape_original_64};

    @Override
    public void setChangeList (List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    public void setEndList (List<Integer> endList) {
        this.endList = endList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH (@NonNull ViewGroup parent, int viewType) {
        RecycleItemMusicBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycle_item_music, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressLint("DefaultLocale")
    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH (@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicImage.setImageResource(images[position]);
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicContent.setText(mList.get(position));
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.itemMusicNumber.setText(String.format("(%d)", endList.size() == 0 ? 0 : endList.get(position)));
        ((ViewHolder<RecycleItemMusicBinding>) holder).mBinding.setIndex(position);

    }
}
