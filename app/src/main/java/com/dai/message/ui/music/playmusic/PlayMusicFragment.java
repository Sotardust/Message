package com.dai.message.ui.music.playmusic;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.IMusicAidlInterface;
import com.dai.message.bean.Music;
import com.dai.message.databinding.FragmentPlayMusicBinding;
import com.dai.message.util.Key;

public class PlayMusicFragment extends BaseFragment {

    private static final String TAG = "PlayMusicFragment";

    private PlayMusicViewModel mViewModel;

    private FragmentPlayMusicBinding mBinding;

    private Music currentMusic;
    private IMusicAidlInterface musicService;

    public static PlayMusicFragment newInstance() {
        return new PlayMusicFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_music, container, false);
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayMusicViewModel.class);
        mBinding.setPlayMusicViewModel(mViewModel);
        if (getArguments() != null) {
            currentMusic = getArguments().getParcelable(Key.MUSIC);
            musicService = (IMusicAidlInterface) getArguments().getBinder(Key.IBINDER);
        }
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        mBinding.songName.setText(currentMusic.name);
        mBinding.author.setText(currentMusic.author);
        mBinding.back.setOnClickListener(this);
        mBinding.playType.setOnClickListener(this);
        mBinding.leftNext.setOnClickListener(this);
        mBinding.play.setOnClickListener(this);
        mBinding.rightNext.setOnClickListener(this);
        mBinding.list.setOnClickListener(this);
    }

    private boolean isPause = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        try {
            switch (view.getId()) {
                case R.id.back:
                    getActivity().finish();
                    break;
                case R.id.playType:
                    break;
                case R.id.leftNext:
                    musicService.pause();
                    break;
                case R.id.play:
                    if (!isPause) {
                        musicService.playMusic(currentMusic);
                        isPause = true;
                    } else {
                        musicService.playPause();
                        isPause = false;
                    }
                    break;
                case R.id.rightNext:
                    musicService.stop();
                    break;
                case R.id.list:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "handlingClickEvents: e", e);
        }
    }
}
