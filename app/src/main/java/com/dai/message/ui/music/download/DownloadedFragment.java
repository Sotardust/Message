package com.dai.message.ui.music.download;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.Music;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentDownloadedBinding;

import java.util.List;

public class DownloadedFragment extends BaseFragment {

    private static final String TAG = "dht";

    private DownloadedViewModel mViewModel;

    private FragmentDownloadedBinding mBinding;
    private DownloadAdapter downloadAdapter;

    public static DownloadedFragment newInstance() {
        return new DownloadedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloaded, container, false);
        initViews(mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DownloadedViewModel.class);
        mBinding.setDownloadedViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);

    }

    @Override
    public void bindViews() {
        super.bindViews();
        downloadAdapter = new DownloadAdapter(recycleItemClickCallBack);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mViewModel.getDownloadedEntityData().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(@Nullable List<Music> entities) {
                mBinding.recyclerView.setLayoutManager(layoutManager);
                downloadAdapter.setChangeList(entities);
            }
        });
        mBinding.recyclerView.setAdapter(downloadAdapter);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(3));
    }

    private RecycleItemClickCallBack<Music> recycleItemClickCallBack = new RecycleItemClickCallBack<Music>() {
        @Override
        public void onItemClickListener(Music value, int position) {
            super.onItemClickListener(value, position);
            Log.d(TAG, "onItemClickListener() called with: value = [" + value + "], position = [" + position + "]");
        }
    };
}
