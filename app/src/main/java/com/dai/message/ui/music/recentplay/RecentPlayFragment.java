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
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentRecentPlayBinding;
import com.dai.message.repository.entity.RecentPlayEntity;
import com.dai.message.ui.music.local.LocalAdapter;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;
import com.dai.message.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class RecentPlayFragment extends BaseFragment {

    private RecentPlayViewModel mViewModel;

    private FragmentRecentPlayBinding mBinding;

    private RecentPlayAdapter recentPlayAdapter;

    private List<RecentPlayEntity> entities = new ArrayList<>();

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recentPlayAdapter = new RecentPlayAdapter(recycleItemClickCallBack, getContext());

        mViewModel.getRecentPlayEntityData().observe(this, new Observer<List<RecentPlayEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecentPlayEntity> entities1) {
                entities.clear();
                entities.addAll(entities1);
                recentPlayAdapter.setChangeList(entities1);
            }
        });
        mBinding.recentRecycler.setAdapter(recentPlayAdapter);
        mBinding.recentRecycler.setLayoutManager(layoutManager);
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
                ToastUtil.toastCustom(getContext(), "功能开发中...", 500);
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

    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        switch (view.getId()) {
            case R.id.recent_play:
                break;
            case R.id.recent_one_week:
                break;
            case R.id.recent_all_time:
                break;

        }
    }
}
