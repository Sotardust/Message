package com.dht.music.ui.local;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseActivity;
import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.NetworkCallback;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.baselib.util.SimpleTextWatcher;
import com.dht.baselib.util.VerticalDecoration;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.music.R;
import com.dht.music.databinding.FragmentLocalBinding;
import com.dht.music.ui.playmusic.PlayMusicActivity;
import com.dht.network.BaseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class LocalFragment extends BaseFragment {

    private static final String TAG = "LocalFragment";

    private LocalViewModel mViewModel;
    private FragmentLocalBinding mBinding;
    private ArrayList<MusicBean> musicList = new ArrayList<>();
    private LocalAdapter localAdapter;

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_local, container, false);
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
        mBinding.localMusicTitleView.setActivity((BaseActivity) getActivity(), mModel);
        mBinding.localTopTitleView.updateView(getActivity(), getString(R.string.local_music));
        mBinding.localTopTitleView.setLocalTitleBar();
        mBinding.localTopTitleView.setSearchEditTextWatcher(textWatcher);
    }


    private SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            Log.d(TAG, "afterTextChanged: s = " + s);
            ArrayList<MusicBean> list = new ArrayList<>();
            for (MusicBean music : musicList) {
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
        if (mBinding.localMusicTitleView != null) {
            mBinding.localMusicTitleView.updateView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        localAdapter = new LocalAdapter(recycleItemClickCallBack);

        mViewModel.getMusicData().observe(this, new Observer<ArrayList<MusicBean>>() {
            @Override
            public void onChanged(ArrayList<MusicBean> musics) {
                musicList.clear();
                musicList.addAll(musics);
                localAdapter.setChangeList(musics);
            }
        });
        mBinding.recyclerView.setAdapter(localAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(3));

    }

    private RecycleItemClickCallBack<MusicBean> recycleItemClickCallBack = new RecycleItemClickCallBack<MusicBean>() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onItemClickListener(int type, MusicBean music, int position) {
            super.onItemClickListener(type, music, position);
            if (type == LocalAdapter.Type.IV.index) {
                List<File> files = new ArrayList<>();
                File file = new File(music.path);
                files.add(file);
                mViewModel.uploadFiles(files, networkCallback);
            } else {
                mViewModel.insertOrUpdate(music);
                MessagePreferences.INSTANCE.setCurrentMusic(music);
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
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
