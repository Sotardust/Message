package com.dai.message.ui.music.download;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dai.message.base.BaseAdapter;
import com.dai.message.bean.Music;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemDownloadBinding;

import java.text.DecimalFormat;
import java.util.List;

/**
 * created by dht on 2019/3/11 17:43
 */
public class DownloadAdapter extends BaseAdapter<Music> {

    public DownloadAdapter(RecycleItemClickCallBack<Music> clickCallBack) {
        this.callBack = clickCallBack;
    }

    @Override
    public void setChangeList(List<Music> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemDownloadBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycle_item_download, parent, false);
        mBinding.setCallback(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Music music = mList.get(position);
            ((ViewHolder<RecycleItemDownloadBinding>) holder).mBinding.songName.setText(music.name);
            ((ViewHolder<RecycleItemDownloadBinding>) holder).mBinding.author.setText(music.author);
            ((ViewHolder<RecycleItemDownloadBinding>) holder).mBinding.setIndex(position);
            WaveProgressView waveProgressView = ((ViewHolder<RecycleItemDownloadBinding>) holder).mBinding.waveProgress;
//            waveProgressView.setTextView(textProgress);
            waveProgressView.setOnAnimationListener(new WaveProgressView.OnAnimationListener() {
                @Override
                public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String s = decimalFormat.format(interpolatedTime * updateNum / maxNum * 100) + "%";
                    return s;
                }

                @Override
                public float howToChangeWaveHeight(float percent, float waveHeight) {
                    return (1 - percent) * waveHeight;
                }
            });
            waveProgressView.setProgressNum(95, 3000);
            waveProgressView.setDrawSecondWave(true);
        }
    }


}
