package com.dai.message.ui.music.recentplay;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dht.commonlib.base.BaseAdapter;
import com.dht.commonlib.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemRecentPlayBinding;
import com.dai.message.repository.entity.RecentPlayEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * created by Administrator on 2018/12/27 16:37
 */
public class RecentPlayAdapter extends BaseAdapter<RecentPlayEntity> {


    private DynamicType dynamicType = DynamicType.PLAY_TIME;

    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss", Locale.CHINA);

    public RecentPlayAdapter(RecycleItemClickCallBack<RecentPlayEntity> recycleItemClickCallBack, Context context) {
        this.context = context;
        this.callBack = recycleItemClickCallBack;
    }

    @Override
    public void setChangeList(List<RecentPlayEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setChangeList(List<RecentPlayEntity> mList, DynamicType dynamicType) {
        this.dynamicType = dynamicType;
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemRecentPlayBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycle_item_recent_play, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            RecentPlayEntity entity = mList.get(position);
            ((ViewHolder<RecycleItemRecentPlayBinding>) holder).mBinding.setRecentPlayEntity(entity);
            ((ViewHolder<RecycleItemRecentPlayBinding>) holder).mBinding.setIndex(position);

            String dynamicValue = null;
            switch (dynamicType) {
                case PLAY_TOTAL:
                    dynamicValue = context.getString(R.string.recent_play_count).replace("[value]", String.valueOf(entity.playTotal));
                    break;
                case PLAY_COUNT:
                    dynamicValue = context.getString(R.string.recent_play_count).replace("[value]", String.valueOf(entity.playCount));
                    break;
                case PLAY_TIME:
                    dynamicValue = context.getString(R.string.recent_play_time).replace("[value]", format.format(entity.playTime));
                    break;
            }
            ((ViewHolder<RecycleItemRecentPlayBinding>) holder).mBinding.setDynamicValue(dynamicValue);
        }
    }

    public enum Type {

        TV(0), // text文本
        IV(1); //more 图片

        public int index;

        Type(int index) {
            this.index = index;
        }
    }

    public enum DynamicType {

        PLAY_TIME(0), // 最近播放时间
        PLAY_COUNT(1), //最近一周播放次数
        PLAY_TOTAL(2); //所有播放次数

        public int index;

        DynamicType(int index) {
            this.index = index;
        }
    }
}
