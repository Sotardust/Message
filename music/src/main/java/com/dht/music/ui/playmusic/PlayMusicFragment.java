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

import com.dht.baselib.base.BaseActivity;
import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.ObservableCallback;
import com.dht.baselib.util.ObservableUtil;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.eventbus.RxBus;
import com.dht.eventbus.event.UpdateTopPlayEvent;
import com.dht.music.R;
import com.dht.music.databinding.FragmentPlayMusicBinding;
import com.dht.music.dialog.PlayListDialogFragment;
import com.dht.baselib.util.PlayType;

import io.reactivex.ObservableEmitter;

/**
 * @author Administrator
 */
public class PlayMusicFragment extends BaseFragment {

    private static final String TAG = "PlayMusicFragment";

    private PlayMusicViewModel mViewModel;


    private FragmentPlayMusicBinding mBinding;


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
        mBinding.playTopTitleView.setActivity(getActivity(),mModel);
        mBinding.playTopTitleView.updatePlayView();
        mBinding.playTopTitleView.setSharedCallback(new LocalCallback<MusicBean>() {
            @Override
            public void onChangeData (MusicBean data) {
                super.onChangeData(data);
                ToastUtil.toastCustom(getContext(), "功能开发中...", 500);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initData () {
        super.initData();
        final MusicBean currentMusic = MessagePreferences.INSTANCE.getCurrentMusic();
        ObservableUtil.execute(new ObservableCallback<Boolean>() {
            @Override
            public void subscribe (ObservableEmitter<Boolean> emitter) throws Exception {
                super.subscribe(emitter);

                boolean isPlaying = mModel.isPlaying();
                if (!currentMusic.toString().equals(mModel.getCurrentMusic().toString())) {
                    emitter.onNext(false);
                    return;
                }
                emitter.onNext(isPlaying);
            }
        }, new LocalCallback<Boolean>() {
            @Override
            public void onChangeData (Boolean data) {
                super.onChangeData(data);
                if (!data) {
                    mModel.playMusic(currentMusic);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        RxBus.getInstance().post(new UpdateTopPlayEvent());
    }

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handlingClickEvents (View view) {
        super.handlingClickEvents(view);
        int i = view.getId();
        if (i == R.id.play_type) {
            playType = ++playType > PlayType.SHUFFLE_PLAYBACK.getIndex() ? playType = 0 : playType;
            mModel.setPlayType(playType);
            mBinding.playType.setText(PlayType.getPlayTypeString(playType));
            ToastUtil.toastCustom(getContext(), PlayType.getPlayTypeString(playType), 500);
            return;
        }
        if (i == R.id.previous) {
            mModel.playPrevious();
            mBinding.play.setText(R.string.playing);
            ToastUtil.toastCustom(getContext(), R.string.previous, 500);
            return;
        }
        if (i == R.id.play) {
            if (mModel.isPlaying()) {
                ToastUtil.toastCustom(getContext(), R.string.pause, 500);
                mModel.pause();
            } else {
                mModel.playPause();
                ToastUtil.toastCustom(getContext(), R.string.playing, 500);
            }
            mBinding.play.setText(mModel.isPlaying() ? R.string.playing : R.string.pause);
            return;
        }
        if (i == R.id.next) {
            mModel.playNext();
            mBinding.play.setText(R.string.playing);
            ToastUtil.toastCustom(getContext(), R.string.next, 500);
            return;
        }
        if (i == R.id.play_list) {
            final PlayListDialogFragment dialogFragment = new PlayListDialogFragment();
            dialogFragment.show((BaseActivity) getActivity(), new LocalCallback<MusicBean>() {
                @Override
                public void onChangeData (MusicBean data) {
                    super.onChangeData(data);
                    mModel.playMusic(data);
                    dialogFragment.dismiss();
                }
            });
        }

    }
}
