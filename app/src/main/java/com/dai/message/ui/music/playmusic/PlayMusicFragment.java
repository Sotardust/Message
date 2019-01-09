package com.dai.message.ui.music.playmusic;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.base.BaseFragment;
import com.dai.message.databinding.FragmentPlayMusicBinding;

import java.io.File;

public class PlayMusicFragment extends BaseFragment {

    private PlayMusicViewModel mViewModel;

    private FragmentPlayMusicBinding mBinding;

    private String path;

    public static PlayMusicFragment newInstance() {
        return new PlayMusicFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_music, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayMusicViewModel.class);
        mBinding.setPlayMusicViewModel(mViewModel);
        path = getArguments().getString("path");
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();

        File file = new File(path);
        mBinding.songName.setText(mViewModel.parseSongName(file.getName()));
        mBinding.author.setText(mViewModel.parseAuthor(file.getName()));
        mBinding.back.setOnClickListener(this);
        mBinding.playType.setOnClickListener(this);
        mBinding.leftNext.setOnClickListener(this);
        mBinding.play.setOnClickListener(this);
        mBinding.rightNext.setOnClickListener(this);
        mBinding.list.setOnClickListener(this);
    }


    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.playType:
                break;
            case R.id.leftNext:
                break;
            case R.id.play:
                mViewModel.playMusic(path);
                break;
            case R.id.rightNext:
                break;
            case R.id.list:
                break;
        }
    }
}
