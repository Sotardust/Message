package com.dai.message.ui.dialog;

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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseDialogFragment;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.Music;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentPlayListDialogBinding;
import com.dai.message.ui.music.local.LocalAdapter;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayListDialogFragment extends BaseDialogFragment {

    private static final String TAG = "PlayListDialogFragment";

    private PlayListDialogViewModel mViewModel;

    private FragmentPlayListDialogBinding mBinding;
    private ArrayList<Music> musicList = new ArrayList<>();

    public static PlayListDialogFragment newInstance() {
        return new PlayListDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.PlayListDialog);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setBottomWindowParams();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_list_dialog, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayListDialogViewModel.class);
        mBinding.setPlayListDialogViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        weakReference.get().getWindowManager().getDefaultDisplay().getMetrics(dm);
        weakReference.get().getWindow().setLayout(dm.widthPixels, weakReference.get().getWindow().getAttributes().height);
    }

    @Override
    public void bindViews() {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        final LocalAdapter localAdapter = new LocalAdapter(recycleItemClickCallBack);

        mViewModel.getMusicData().observe(this, new Observer<ArrayList<Music>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Music> musics) {
                musicList.clear();
                musicList.addAll(musics);
                localAdapter.setChangeList(musics);
            }
        });
        mBinding.recyclePlayList.setAdapter(localAdapter);
        mBinding.recyclePlayList.setLayoutManager(layoutManager);
        mBinding.recyclePlayList.addItemDecoration(new VerticalDecoration(3));
    }

    private RecycleItemClickCallBack<String> recycleItemClickCallBack = new RecycleItemClickCallBack<String>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener(int type, String value, int position) {
            super.onItemClickListener(type, value, position);
            Log.d(TAG, "onItemClickListener: path = " + musicList.get(position).path);
            String path = musicList.get(position).path;
            if (type == LocalAdapter.Type.IV.index) {
//                List<File> files = new ArrayList<>();
//                File file = new File(path);
//                files.add(file);
//                mViewModel.uploadFiles(files, networkCallback);
            } else {
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                intent.putExtra(Key.MUSIC, musicList.get(position));
                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putBinder(Key.IBINDER, getArguments().getBinder(Key.IBINDER));
                intent.putExtra(Key.IBINDER, bundle);
                startActivity(intent);
            }
        }
    };
}
