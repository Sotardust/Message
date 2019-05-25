package com.dht.music.dialog;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseAdapter;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.R;
import com.dht.music.databinding.RecycleItemPlayListBinding;
import com.dht.music.util.ViewHolder;

import java.util.List;

/**
 * 播放列表适配器
 * <p>
 * created by dht on 2018/12/27 16:37
 *
 * @author Administrator
 */
public class PlayListAdapter extends BaseAdapter<MusicBean> {


    public PlayListAdapter (RecycleItemClickCallBack<MusicBean> recycleItemClickCallBack) {
        this.callBack = recycleItemClickCallBack;
    }

    @Override
    public void setChangeList (List<MusicBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH (@NonNull ViewGroup parent, int viewType) {
        RecycleItemPlayListBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycle_item_play_list, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH (@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder<RecycleItemPlayListBinding>) holder).mBinding.setMusic(mList.get(position));
            ((ViewHolder<RecycleItemPlayListBinding>) holder).mBinding.setIndex(position);
        }
    }

    public enum Type {

        // text文本
        TV(0),
        //删除 图片
        IV(1);

        public int index;

        Type (int index) {
            this.index = index;
        }
    }
}
