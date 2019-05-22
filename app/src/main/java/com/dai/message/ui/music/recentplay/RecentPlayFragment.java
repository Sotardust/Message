package com.dai.message.ui.music.recentplay;

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

import com.dai.message.R;
import com.dht.baselib.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentRecentPlayBinding;
import com.dai.message.repository.entity.RecentPlayEntity;
import com.dai.message.ui.music.local.LocalAdapter;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;
import com.dai.message.util.ToastUtil;

import java.util.List;

public class RecentPlayFragment extends BaseFragment {


    private static final String TAG = "RecentPlayFragment";

    private RecentPlayViewModel mViewModel;

    private FragmentRecentPlayBinding mBinding;

    private RecentPlayAdapter recentPlayAdapter;


    private LinearLayoutManager layoutManager;
    private RecentPlayAdapter.DynamicType dynamicType = RecentPlayAdapter.DynamicType.PLAY_TIME;

    public static RecentPlayFragment newInstance() {
        return new RecentPlayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_play, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecentPlayViewModel.class);
        mBinding.setRecentPlayViewModel(mViewModel);
        initViews(mBinding.getRoot());
        bindViews();
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        mBinding.recentTopTitleView.updateView(getActivity(), getString(R.string.listening_to_songs));
    }

    @Override
    public void bindViews() {
        super.bindViews();
        recentPlayAdapter = new RecentPlayAdapter(recycleItemClickCallBack, getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mViewModel.getRecentPlayEntityData().observe(this, new Observer<List<RecentPlayEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecentPlayEntity> entities) {
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

    private RecycleItemClickCallBack<RecentPlayEntity> recycleItemClickCallBack = new RecycleItemClickCallBack<RecentPlayEntity>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener(int type, RecentPlayEntity entity, int position) {
            super.onItemClickListener(type, entity, position);
            if (type == LocalAdapter.Type.IV.index) {
                mViewModel.deleteCurrentRecentEntity(entity.songName, dynamicType);
                ToastUtil.toastCustom(getContext(), R.string.delete_successful, 500);
            } else {
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                intent.putExtra(Key.MUSIC, entity.music);
                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putBinder(Key.IBINDER, getArguments().getBinder(Key.IBINDER));
                intent.putExtra(Key.IBINDER, bundle);
                startActivity(intent);
            }
        }
    };

    private boolean isReverse = true;

    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        layoutManager.setReverseLayout(isReverse);
        switch (view.getId()) {
            case R.id.recent_play:
                dynamicType = RecentPlayAdapter.DynamicType.PLAY_TIME;
                mViewModel.getAscRecentPlayTime();
                break;
            case R.id.recent_one_week:
                dynamicType = RecentPlayAdapter.DynamicType.PLAY_COUNT;
                mViewModel.getAscRecentOneWeek();
                break;
            case R.id.recent_all_time:
                dynamicType = RecentPlayAdapter.DynamicType.PLAY_TOTAL;
                mViewModel.getAscRecentAllTime();
                break;
        }
        isReverse = !isReverse;
    }
}
