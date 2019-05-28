package com.dht.music.ui.recentplay;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseAdapter;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.databaselib.bean.music.RecentPlayBean;
import com.dht.music.R;
import com.dht.music.databinding.RecycleItemRecentPlayBinding;
import com.dht.music.util.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * created by Administrator on 2018/12/27 16:37
 *
 * @author Administrator
 */
public class RecentPlayAdapter extends BaseAdapter<RecentPlayBean> {


    private DynamicType dynamicType = DynamicType.PLAY_TIME;

    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss", Locale.CHINA);

    public RecentPlayAdapter (RecycleItemClickCallBack<RecentPlayBean> recycleItemClickCallBack, Context context) {
        this.context = context;
        this.callBack = recycleItemClickCallBack;
    }

    @Override
    public void setChangeList (List<RecentPlayBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setChangeList (List<RecentPlayBean> mList, DynamicType dynamicType) {
        this.dynamicType = dynamicType;
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH (@NonNull ViewGroup parent, int viewType) {
        RecycleItemRecentPlayBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycle_item_recent_play, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH (@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            RecentPlayBean entity = mList.get(position);
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
                default:
            }
            ((ViewHolder<RecycleItemRecentPlayBinding>) holder).mBinding.setDynamicValue(dynamicValue);
        }
    }

    public enum Type {
        // text文本
        TV(0),
        //more 图片
        IV(1);

        public int index;

        Type (int index) {
            this.index = index;
        }
    }

    public enum DynamicType {
        // 最近播放时间
        PLAY_TIME(0),
        //最近一周播放次数
        PLAY_COUNT(1),
        //所有播放次数
        PLAY_TOTAL(2);

        public int index;

        DynamicType (int index) {
            this.index = index;
        }
    }
}
