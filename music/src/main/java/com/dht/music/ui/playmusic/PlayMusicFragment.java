package com.dht.music.ui.playmusic;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseFragment;
import com.dht.music.R;
import com.dht.music.databinding.FragmentPlayMusicBinding;
import com.dht.music.util.PlayType;

/**
 * @author Administrator
 */
public class PlayMusicFragment extends BaseFragment {

    private static final String TAG = "PlayMusicFragment";

    private PlayMusicViewModel mViewModel;


    private FragmentPlayMusicBinding mBinding;

//    private IMusicAidlInterface musicService;


    private int playType = 0;

    public static PlayMusicFragment newInstance () {
        return new PlayMusicFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_music, container, false);
        return mBinding.getRoot();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayMusicViewModel.class);
        mBinding.setPlayMusicViewModel(mViewModel);

        initData();
        bindViews();
        initViews(mBinding.getRoot());
    }

    @Override
    public void initViews (View view) {
        super.initViews(view);
//        mBinding.playTopTitleView.setActivity(getActivity());
//        mBinding.playTopTitleView.updatePlayView(Config.getInstance().getCurrentMusic());
//        mBinding.playTopTitleView.setSharedCallback(new LocalCallback<MusicBean>() {
//            @Override
//            public void onChangeData (MusicBean data) {
//                super.onChangeData(data);
//                ToastUtil.toastCustom(getContext(), "功能开发中...", 500);
//            }
//        });
    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//    @Override
//    public void initData () {
//        super.initData();
//        if (getArguments() == null) {
//            return;
//        }
//        final Music currentMusic = getArguments().getParcelable(Key.MUSIC);
//        musicService = (IMusicAidlInterface) getArguments().getBinder(Key.IBINDER);
//        if (musicService == null || currentMusic == null) {
//            return;
//        }
//        ObservableUtil.execute(new ObservableCallback<Boolean>() {
//            @Override
//            public void subscribe (ObservableEmitter<Boolean> emitter) throws Exception {
//                super.subscribe(emitter);
//                boolean isPlaying = musicService.isPlaying();
//                if (!currentMusic.toString().equals(Config.getInstance().getCurrentMusic().toString())) {
//                    emitter.onNext(false);
//                    return;
//                }
//                emitter.onNext(isPlaying);
//            }
//        }, new LocalCallback<Boolean>() {
//            @Override
//            public void onChangeData (Boolean data) {
//                super.onChangeData(data);
//                try {
//                    if (!data) {
//                        musicService.playMusic(currentMusic);
//                        mBinding.playTopTitleView.updatePlayView(currentMusic);
//                    }
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    public void bindViews () {
        super.bindViews();
        mBinding.playType.setOnClickListener(this);
        mBinding.previous.setOnClickListener(this);
        mBinding.play.setOnClickListener(this);
        mBinding.next.setOnClickListener(this);
        mBinding.playList.setOnClickListener(this);
        mBinding.playType.setText(PlayType.getPlayTypeString(playType));
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void handlingClickEvents (View view) {
//        super.handlingClickEvents(view);
//        try {
//            switch (view.getId()) {
//                case R.id.play_type:
//                    playType = ++playType > PlayType.SHUFFLE_PLAYBACK.getIndex() ? playType = 0 : playType;
//                    musicService.setPlayType(playType);
//                    mBinding.playType.setText(PlayType.getPlayTypeString(playType));
//                    ToastUtil.toastCustom(getContext(), PlayType.getPlayTypeString(playType), 500);
//                    break;
//                case R.id.previous:
//                    musicService.playPrevious();
//                    mBinding.playTopTitleView.updatePlayView(musicService.getCurrentMusic());
//                    mBinding.play.setText(R.string.playing);
//                    ToastUtil.toastCustom(getContext(), R.string.previous, 500);
//                    break;
//                case R.id.play:
//                    if (musicService.isPlaying()) {
//                        ToastUtil.toastCustom(getContext(), R.string.pause, 500);
//                        musicService.pause();
//                    } else {
//                        musicService.playPause();
//                        ToastUtil.toastCustom(getContext(), R.string.playing, 500);
//                    }
//                    mBinding.play.setText(musicService.isPlaying() ? R.string.playing : R.string.pause);
//                    break;
//                case R.id.next:
//                    musicService.playNext();
//                    mBinding.play.setText(R.string.playing);
//                    mBinding.playTopTitleView.updatePlayView(musicService.getCurrentMusic());
//                    ToastUtil.toastCustom(getContext(), R.string.next, 500);
//                    break;
//                case R.id.play_list:
//                    PlayListDialogFragment playMusicFragment = new PlayListDialogFragment();
//                    playMusicFragment.setArguments(getArguments());
//                    playMusicFragment.show((BaseActivity) getActivity(), new LocalCallback<MusicBean>() {
//                        @Override
//                        public void onChangeData (MusicBean data) {
//                            super.onChangeData(data);
//                            try {
//                                mBinding.playTopTitleView.updatePlayView(data);
//                                musicService.playMusic(data);
//                            } catch (RemoteException e) {
//                                e.printStackTrace();
//                                Log.e(TAG, "onChangeData: e", e);
//                            }
//                        }
//                    });
//                    break;
//                default:
//                    break;
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            Log.e(TAG, "handlingClickEvents: e", e);
//        }
//    }
}
