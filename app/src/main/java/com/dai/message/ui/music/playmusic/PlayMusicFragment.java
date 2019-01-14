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
import com.dai.message.util.PlayType;
import com.dai.message.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends BaseFragment {

    private static final String TAG = "PlayMusicFragment";

    private PlayMusicViewModel mViewModel;

    private FragmentPlayMusicBinding mBinding;

    private Music currentMusic;

    private IMusicAidlInterface musicService;

    private boolean isPause = false;

    private int playType = -1;
    private int index = 0;

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
        initData();
        bindViews();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initData() {
        super.initData();
        try {
            if (getArguments() != null) {
                index = getArguments().getInt(Key.INDEX);
                currentMusic = getArguments().getParcelable(Key.MUSIC);
                musicService = (IMusicAidlInterface) getArguments().getBinder(Key.IBINDER);
                if (musicService != null) {
                    musicService.initPlayList();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "initData: e", e);
        }
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

    private List<Music> list = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        try {
            if (list.size() == 0) {
                list.addAll(musicService.getPlayList());
                currentMusic = list.get(index);
            }
            switch (view.getId()) {
                case R.id.back:
                    getActivity().finish();
                    break;
                case R.id.playType:
                    playType = ++playType > PlayType.SHUFFLE_PLAYBACK.getIndex() ? playType = 0 : playType;
                    Log.d(TAG, "handlingClickEvents: playType = "+ playType);
                    musicService.setPlayType(playType);
                    ToastUtil.toastShort(getContext(), PlayType.getPlayTypeString(playType));
                    break;
                case R.id.leftNext:
                    musicService.playPrevious();
//                    currentMusic = musicService.getCurrentMusic();
                    break;
                case R.id.play:
                    if (!isPause) {
                        musicService.playMusic(currentMusic);
                        isPause = true;
                    }
                    if (musicService.isPlaying()) {
                        musicService.pause();
                    } else {
                        musicService.playPause();
                    }
//                    currentMusic = musicService.getCurrentMusic();
                    break;
                case R.id.rightNext:
                    musicService.playNext();
//                    currentMusic = musicService.getCurrentMusic();
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
