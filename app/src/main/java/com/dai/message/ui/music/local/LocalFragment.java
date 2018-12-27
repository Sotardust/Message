package com.dai.message.ui.music.local;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentLocalBinding;

import java.io.File;
import java.util.ArrayList;

public class LocalFragment extends BaseFragment {

    private static final String TAG = "LocalFragment";

    private LocalViewModel mViewModel;

    protected FragmentLocalBinding mBinding;

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


        mViewModel.getFilesListData().observe(this, new Observer<ArrayList<File>>() {


            @Override
            public void onChanged(@Nullable ArrayList<File> files) {

                if (files == null) return;
                ArrayList<String> songList = new ArrayList<>();
                ArrayList<String> userList = new ArrayList<>();
                for (File file : files) {
                    songList.add(mViewModel.parseSongName(file.getName()));
                    userList.add(mViewModel.parseUsername(file.getName()));
                }
                localAdapter.setUsernameList(userList);
                localAdapter.setChangeList(songList);
            }
        });
        mBinding.recyclerView.setAdapter(localAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(3));

    }

    private RecycleItemClickCallBack<String> recycleItemClickCallBack = new RecycleItemClickCallBack<String>() {

        @Override
        public void onItemClickListener(String value, int position) {
            super.onItemClickListener(value, position);
            Log.d(TAG, "onItemClickListener() called with: value = [" + value + "], position = [" + position + "]");
        }

        @Override
        public void onItemClickListener(int type, String value, int position) {
            super.onItemClickListener(type, value, position);
            Log.d(TAG, "onItemClickListener() called with: type = [" + type + "], value = [" + value + "], position = [" + position + "]");
        }
    };
}