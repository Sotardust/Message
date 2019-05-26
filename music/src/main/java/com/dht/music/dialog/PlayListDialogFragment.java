package com.dht.music.dialog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.dht.baselib.base.BaseActivity;
import com.dht.baselib.base.BaseDialogFragment;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.baselib.util.ToastUtil;
import com.dht.baselib.util.VerticalDecoration;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.R;
import com.dht.music.databinding.FragmentPlayListDialogBinding;
import com.dht.music.ui.playmusic.PlayMusicActivity;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class PlayListDialogFragment extends BaseDialogFragment {

    private static final String TAG = "PlayListDialogFragment";

    private PlayListDialogViewModel mViewModel;


    private FragmentPlayListDialogBinding mBinding;

    public static PlayListDialogFragment newInstance () {
        return new PlayListDialogFragment();
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PlayListDialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        setBottomWindowParams();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_list_dialog, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayListDialogViewModel.class);
        mBinding.setPlayListDialogViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void onStart () {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            DisplayMetrics dm = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
            window.setLayout(dm.widthPixels, window.getAttributes().height);
        }
    }

    @Override
    public void bindViews () {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        final PlayListAdapter playListAdapter = new PlayListAdapter(recycleItemClickCallBack);

        mViewModel.getMusicData().observe(this, new Observer<ArrayList<MusicBean>>() {
            @Override
            public void onChanged (@Nullable ArrayList<MusicBean> musics) {
                playListAdapter.setChangeList(musics);
            }
        });
        mBinding.recyclePlayList.setAdapter(playListAdapter);
        mBinding.recyclePlayList.setLayoutManager(layoutManager);
        mBinding.recyclePlayList.addItemDecoration(new VerticalDecoration(3));
    }

    private RecycleItemClickCallBack<MusicBean> recycleItemClickCallBack = new RecycleItemClickCallBack<MusicBean>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener (int type, MusicBean music, int position) {
            super.onItemClickListener(type, music, position);
            if (type == PlayListAdapter.Type.IV.index) {
                ToastUtil.toastCustom(getContext(), "功能开发中...", 500);
            } else {
                dismiss();
                if (localCallback == null) {
                    Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                    startActivity(intent);
                } else {
                    localCallback.onChangeData(music);
                }
            }
        }
    };

    private LocalCallback<MusicBean> localCallback;

    /**
     * 显示播放列表页 并回调点击事件
     *
     * @param activity      BaseActivity
     * @param localCallback 回到接口
     */
    public void show (BaseActivity activity, LocalCallback<MusicBean> localCallback) {
        this.localCallback = localCallback;
        show(activity);
    }

}
