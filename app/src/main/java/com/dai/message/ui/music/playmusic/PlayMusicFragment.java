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
import com.dai.message.repository.preferences.Config;
import com.dai.message.ui.view.TopTitleView;
import com.dai.message.util.Key;
import com.dai.message.util.PlayType;
import com.dai.message.util.ToastUtil;

public class PlayMusicFragment extends BaseFragment {

    private static final String TAG = "PlayMusicFragment";

    private PlayMusicViewModel mViewModel;

    private FragmentPlayMusicBinding mBinding;

    private Music currentMusic;

    private IMusicAidlInterface musicService;

    private int playType = Config.getInstance().getPlayType().getIndex();

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
        initViews(mBinding.getRoot());
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        TopTitleView topTitleView = view.findViewById(R.id.play_top_title_view);
        topTitleView.setActivity(getActivity());
        topTitleView.updatePlayView(Config.getInstance().getCurrentMusic());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initData() {
        super.initData();
        try {
            if (getArguments() != null) {
                currentMusic = getArguments().getParcelable(Key.MUSIC);
                musicService = (IMusicAidlInterface) getArguments().getBinder(Key.IBINDER);
                if (musicService != null) {
                    if (!currentMusic.toString().equals(Config.getInstance().getCurrentMusic().toString()) || !musicService.isPlaying()) {
                        musicService.playMusic(currentMusic);
                    }
                    //play音乐有延迟
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void bindViews() {
        super.bindViews();
        mBinding.playType.setOnClickListener(this);
        mBinding.previous.setOnClickListener(this);
        mBinding.play.setOnClickListener(this);
        mBinding.next.setOnClickListener(this);
        mBinding.playList.setOnClickListener(this);
        mBinding.playType.setText(PlayType.getPlayTypeString(playType));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        try {
            switch (view.getId()) {
                case R.id.play_type:
                    playType = ++playType > PlayType.SHUFFLE_PLAYBACK.getIndex() ? playType = 0 : playType;
                    musicService.setPlayType(playType);
                    mBinding.playType.setText(PlayType.getPlayTypeString(playType));
                    ToastUtil.toastCustom(getContext(), PlayType.getPlayTypeString(playType), 500);
                    break;
                case R.id.previous:
                    musicService.playPrevious();
                    ToastUtil.toastCustom(getContext(), R.string.previous, 500);
                    break;
                case R.id.play:
                    if (musicService.isPlaying()) {
                        ToastUtil.toastCustom(getContext(), R.string.pause, 500);
                        musicService.pause();
                    } else {
                        musicService.playPause();
                        ToastUtil.toastCustom(getContext(), R.string.playing, 500);
                    }
                    mBinding.play.setText(musicService.isPlaying() ? R.string.playing : R.string.pause);
                    break;
                case R.id.next:
                    musicService.playNext();
                    ToastUtil.toastCustom(getContext(), R.string.next, 500);
                    break;
                case R.id.play_list:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "handlingClickEvents: e", e);
        }
    }
}
