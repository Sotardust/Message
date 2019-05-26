package com.dht.music.ui.recentplay;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.baselib.util.ToastUtil;
import com.dht.baselib.util.VerticalDecoration;
import com.dht.databaselib.bean.music.RecentPlayBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.music.R;
import com.dht.music.databinding.FragmentRecentPlayBinding;
import com.dht.music.ui.local.LocalAdapter;
import com.dht.music.ui.playmusic.PlayMusicActivity;

import java.util.List;

public class RecentPlayFragment extends BaseFragment {


    private static final String TAG = "RecentPlayFragment";

    private RecentPlayViewModel mViewModel;

    private FragmentRecentPlayBinding mBinding;

    private RecentPlayAdapter recentPlayAdapter;

    private LinearLayoutManager layoutManager;
    private RecentPlayAdapter.DynamicType dynamicType = RecentPlayAdapter.DynamicType.PLAY_TIME;

    public static RecentPlayFragment newInstance () {
        return new RecentPlayFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_play, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecentPlayViewModel.class);
        mBinding.setRecentPlayViewModel(mViewModel);
        initViews(mBinding.getRoot());
        bindViews();
    }

    @Override
    public void initViews (View view) {
        super.initViews(view);
        mBinding.recentTopTitleView.updateView(getActivity(), getString(R.string.listening_to_songs));
    }

    @Override
    public void bindViews () {
        super.bindViews();
        recentPlayAdapter = new RecentPlayAdapter(recycleItemClickCallBack, getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mViewModel.getRecentPlayEntityData().observe(this, new Observer<List<RecentPlayBean>>() {
            @Override
            public void onChanged (@Nullable List<RecentPlayBean> entities) {
                mBinding.recentRecycler.setLayoutManager(layoutManager);
                recentPlayAdapter.setChangeList(entities, dynamicType);
            }
        });
        mBinding.recentRecycler.setAdapter(recentPlayAdapter);
        mBinding.recentRecycler.addItemDecoration(new VerticalDecoration(3));
        mBinding.recentPlay.setOnClickListener(this);
        mBinding.recentOneWeek.setOnClickListener(this);
        mBinding.recentAllTime.setOnClickListener(this);
    }

    private RecycleItemClickCallBack<RecentPlayBean> recycleItemClickCallBack = new RecycleItemClickCallBack<RecentPlayBean>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener (int type, RecentPlayBean entity, int position) {
            super.onItemClickListener(type, entity, position);
            if (type == LocalAdapter.Type.IV.index) {
                mViewModel.deleteCurrentRecentEntity(entity.songName, dynamicType);
                ToastUtil.toastCustom(getContext(), R.string.delete_successful, 500);
            } else {
                MessagePreferences.getInstance().setCurrentMusic(entity.music);
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                startActivity(intent);
                onDestroy();
            }
        }
    };

    private boolean isReverse = true;

    @Override
    public void handlingClickEvents (View view) {
        super.handlingClickEvents(view);
        layoutManager.setReverseLayout(isReverse);
        int i = view.getId();
        if (i == R.id.recent_play) {
            dynamicType = RecentPlayAdapter.DynamicType.PLAY_TIME;
            mViewModel.getAscRecentPlayTime();
        } else if (i == R.id.recent_one_week) {
            dynamicType = RecentPlayAdapter.DynamicType.PLAY_COUNT;
            mViewModel.getAscRecentOneWeek();
        } else if (i == R.id.recent_all_time) {
            dynamicType = RecentPlayAdapter.DynamicType.PLAY_TOTAL;
            mViewModel.getAscRecentAllTime();
        }
        isReverse = !isReverse;
    }
}
