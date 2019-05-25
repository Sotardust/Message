package com.dht.music.ui.cloud;

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

import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.NetworkCallback;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.baselib.util.VerticalDecoration;
import com.dht.databaselib.bean.music.CloudMusicBean;
import com.dht.music.R;
import com.dht.music.databinding.FragmentCloudDiskBinding;
import com.dht.network.BaseModel;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 */
public class CloudDiskFragment extends BaseFragment {

    private static final String TAG = "CloudDiskFragment";

    private CloudDiskViewModel mViewModel;

    private FragmentCloudDiskBinding mBinding;

    public static CloudDiskFragment newInstance () {
        return new CloudDiskFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cloud_disk, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CloudDiskViewModel.class);
        mBinding.setCloudDiskViewModel(mViewModel);
        bindViews();
    }

    private CloudDiskAdapter localAdapter;

    @Override
    public void bindViews () {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        localAdapter = new CloudDiskAdapter(recycleItemClickCallBack);
        mViewModel.getMusicList(callback);
        mBinding.recyclerView.setAdapter(localAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(3));

    }

    private NetworkCallback<BaseModel<List<CloudMusicBean>>> callback = new NetworkCallback<BaseModel<List<CloudMusicBean>>>() {
        @Override
        public void onChangeData (BaseModel<List<CloudMusicBean>> data) {
            Log.d(TAG, "onChangeData: data = " + data);
            if (data == null) {
                return;
            }
            List<CloudMusicBean> entities = new ArrayList<>();
            entities.addAll(data.result);
            ArrayList<String> names = new ArrayList<>();
            for (CloudMusicBean entity : entities) {
                names.add(entity.name);
            }
            localAdapter.setUsernameList(names);
            localAdapter.setChangeList(names);
        }
    };

    private RecycleItemClickCallBack<String> recycleItemClickCallBack = new RecycleItemClickCallBack<String>() {

        @Override
        public void onItemClickListener (String value, int position) {
            super.onItemClickListener(value, position);
            Log.d(TAG, "onItemClickListener() called with: value = [" + value + "], position = [" + position + "]");
            mViewModel.downloadMusic(value);
        }

    };


}
