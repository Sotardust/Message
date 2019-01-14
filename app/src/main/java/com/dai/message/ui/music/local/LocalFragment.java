package com.dai.message.ui.music.local;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.BaseModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentLocalBinding;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalFragment extends BaseFragment {

    private static final String TAG = "LocalFragment";

    private LocalViewModel mViewModel;
    private FragmentLocalBinding mBinding;
    private ArrayList<Music> musicList = new ArrayList<>();

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_local, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LocalViewModel.class);
        mBinding.setLocalViewModel(mViewModel);
        bindViews();
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
                Log.d(TAG, "onItemClickListener: musicList.get(position) = " + musicList.get(position).toString());
                Log.d(TAG, "onItemClickListener: musicList.get(position) = " + musicList.get(position));

                intent.putExtra(Key.MUSIC, musicList.get(position));
                intent.putExtra(Key.INDEX, position);
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
