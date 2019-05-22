package com.dai.message.ui.music;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dht.baselib.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentMusicBinding;
import com.dai.message.ui.music.cloud.CloudDiskActivity;
import com.dai.message.ui.music.download.DownloadActivity;
import com.dai.message.ui.music.local.LocalActivity;
import com.dai.message.ui.music.recentplay.RecentPlayActivity;
import com.dai.message.util.Key;

import java.util.Arrays;
import java.util.List;

public class MusicFragment extends BaseFragment {

    private MusicViewModel mViewModel;

    private FragmentMusicBinding mBinding;

    private MusicAdapter musicAdapter;

    public static MusicFragment newInstance() {
        return new MusicFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_music, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MusicViewModel.class);
        mBinding.setMusicViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        musicAdapter = new MusicAdapter(recycleItemClickCallBack);
        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.musicList));
        musicAdapter.setChangeList(list);
        mBinding.recyclerView.setAdapter(musicAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(3));
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getEndListData().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> totalList) {
                musicAdapter.setEndList(totalList);
            }
        });
    }

    private RecycleItemClickCallBack<String> recycleItemClickCallBack = new RecycleItemClickCallBack<String>() {

        @Override
        public void onItemClickListener(String value, int position) {
            super.onItemClickListener(value, position);
            switch (position) {
                case 0:
                    startActivity(new Intent(getActivity(), LocalActivity.class)
                            .putExtra(Key.IBINDER, getArguments()));
                    break;
                case 1:
                    startActivity(new Intent(getActivity(), RecentPlayActivity.class)
                            .putExtra(Key.IBINDER, getArguments()));
                    break;
                case 2:
                    startActivity(new Intent(getActivity(), CloudDiskActivity.class)
                            .putExtra(Key.IBINDER, getArguments()));
                    break;

                case 3:
                    startActivity(new Intent(getActivity(), DownloadActivity.class)
                            .putExtra(Key.IBINDER, getArguments()));
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
