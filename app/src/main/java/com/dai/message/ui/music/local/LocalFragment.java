package com.dai.message.ui.music.local;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.BaseModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentLocalBinding;
import com.dai.message.receiver.BaseReceiver;
import com.dai.message.receiver.SendLocalBroadcast;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.ui.view.MusicTitleView;
import com.dai.message.ui.view.TopTitleView;
import com.dai.message.util.Key;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalFragment extends BaseFragment {

    private static final String TAG = "LocalFragment";

    private LocalViewModel mViewModel;
    private FragmentLocalBinding mBinding;
    private ArrayList<Music> musicList = new ArrayList<>();
    private BaseReceiver receiver;
    private MusicTitleView musicTitleView;


    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_local, container, false);
        receiver = new BaseReceiver<>(localCallback);
        LocalBroadcastManager.getInstance(getContext().getApplicationContext()).registerReceiver(receiver,
                new IntentFilter(SendLocalBroadcast.KEY_UPDATE_MUSIC_TITLE_VIEW));
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LocalViewModel.class);
        mBinding.setLocalViewModel(mViewModel);
        initViews(mBinding.getRoot());
        bindViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initViews(View view) {
        super.initViews(view);
        musicTitleView = view.findViewById(R.id.local_music_title_view);
        Bundle bundle = new Bundle();
        bundle.putBinder(Key.IBINDER, getArguments().getBinder(Key.IBINDER));
        musicTitleView.setBundleData(bundle);
        musicTitleView.setActivity(getActivity());

        TopTitleView topTitleView = view.findViewById(R.id.local_top_title_view);
        topTitleView.setActivity(getActivity());
        topTitleView.updateView(getString(R.string.local_music));
        topTitleView.setViewVisibility();

    }

    private LocalCallback<Integer> localCallback = new LocalCallback<Integer>() {
        @Override
        public void onChangeData(Integer data) {
            Log.d("MusicTitleView", "LocalFragment onChangeData: data = " + data);
            musicTitleView.updateView(true);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (musicTitleView != null)
            musicTitleView.updateResumeView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getContext() == null) return;
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
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
        mBinding.recyclerView.setAdapter(localAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(3));

    }

    private RecycleItemClickCallBack<String> recycleItemClickCallBack = new RecycleItemClickCallBack<String>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener(int type, String value, int position) {
            super.onItemClickListener(type, value, position);
            Log.d(TAG, "onItemClickListener: path = " + musicList.get(position).path);
            String path = musicList.get(position).path;
            if (type == LocalAdapter.Type.IV.index) {
                List<File> files = new ArrayList<>();
                File file = new File(path);
                files.add(file);
                mViewModel.uploadFiles(files, networkCallback);
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

    private NetworkCallback<BaseModel<ArrayList<String>>> networkCallback = new NetworkCallback<BaseModel<ArrayList<String>>>() {
        @Override
        public void onChangeData(BaseModel<ArrayList<String>> data) {

            Log.d(TAG, "onChangeData: data = " + data);
        }
    };
}
