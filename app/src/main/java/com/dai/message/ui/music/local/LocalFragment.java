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
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.BaseModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.databinding.FragmentLocalBinding;
import com.dai.message.receiver.BaseReceiver;
import com.dai.message.receiver.SendLocalBroadcast;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;
import com.dai.message.util.SimpleTextWatcher;
import com.dht.commonlib.base.BaseActivity;
import com.dht.commonlib.callback.RecycleItemClickCallBack;
import com.dht.commonlib.util.VerticalDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalFragment extends BaseFragment {

    private static final String TAG = "LocalFragment";

    private LocalViewModel mViewModel;
    private FragmentLocalBinding mBinding;
    private ArrayList<Music> musicList = new ArrayList<>();
    private BaseReceiver receiver;
    private LocalAdapter localAdapter;

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_local, container, false);
        receiver = new BaseReceiver<>(receiverCallback);
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
        Bundle bundle = new Bundle();
        bundle.putBinder(Key.IBINDER, getArguments().getBinder(Key.IBINDER));
        mBinding.localMusicTitleView.setBundleData(bundle);
        mBinding.localMusicTitleView.setActivity((BaseActivity) getActivity());

        mBinding.localTopTitleView.updateView(getActivity(),getString(R.string.local_music));
        mBinding.localTopTitleView.setLocalTitleBar();
        mBinding.localTopTitleView.setSearchEditTextWatcher(textWatcher);

    }

    private LocalCallback<Integer> receiverCallback = new LocalCallback<Integer>() {
        @Override
        public void onChangeData(Integer data) {
            Log.d("MusicTitleView", "LocalFragment onChangeData: data = " + data);
            mBinding.localMusicTitleView.updateView();
        }
    };

    private SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            Log.d(TAG, "afterTextChanged: s = " + s);
            ArrayList<Music> list = new ArrayList<>();
            for (Music music : musicList) {
                if (music.name.contains(s.toString()) || music.name.contains(s.toString().toUpperCase())) {
                    list.add(music);
                }
            }
            localAdapter.setChangeList(list);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (mBinding.localMusicTitleView != null)
            mBinding.localMusicTitleView.updateView();
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
        localAdapter = new LocalAdapter(recycleItemClickCallBack);

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

    private RecycleItemClickCallBack<Music> recycleItemClickCallBack = new RecycleItemClickCallBack<Music>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener(int type, Music music, int position) {
            super.onItemClickListener(type, music, position);
            if (type == LocalAdapter.Type.IV.index) {
                List<File> files = new ArrayList<>();
                File file = new File(music.path);
                files.add(file);
                mViewModel.uploadFiles(files, networkCallback);
            } else {
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                intent.putExtra(Key.MUSIC, music);
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
