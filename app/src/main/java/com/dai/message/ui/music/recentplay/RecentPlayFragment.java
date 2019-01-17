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
import com.dai.message.bean.Music;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentRecentPlayBinding;
import com.dai.message.ui.music.local.LocalAdapter;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;
import com.dai.message.util.ToastUtil;

import java.util.List;

public class RecentPlayFragment extends BaseFragment {

    private RecentPlayViewModel mViewModel;

    private FragmentRecentPlayBinding mBinding;

    private LocalAdapter localAdapter;

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
        mBinding.recentTopTitleView.updateView(getActivity(), getString(R.string.recent_play));
    }

    @Override
    public void bindViews() {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        localAdapter = new LocalAdapter(recycleItemClickCallBack);

        mViewModel.getMusicData().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(@Nullable List<Music> musics) {
                localAdapter.setChangeList(musics);
            }
        });
        mBinding.recentRecycler.setAdapter(localAdapter);
        mBinding.recentRecycler.setLayoutManager(layoutManager);
        mBinding.recentRecycler.addItemDecoration(new VerticalDecoration(3));
    }

    private RecycleItemClickCallBack<Music> recycleItemClickCallBack = new RecycleItemClickCallBack<Music>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener(int type, Music music, int position) {
            super.onItemClickListener(type, music, position);
            if (type == LocalAdapter.Type.IV.index) {
                ToastUtil.toastCustom(getContext(), "功能开发中...", 500);
            } else {
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                intent.putExtra(Key.MUSIC, music);
                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putBinder(Key.IBINDER, getArguments().getBinder(Key.IBINDER));
                intent.putExtra(Key.IBINDER, bundle);
                startActivity(intent);
            }
        }
    };
}
