package com.dai.message.ui.music.download;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.base.BaseFragment;
import com.dai.message.databinding.FragmentDownloadingBinding;

public class DownloadingFragment extends BaseFragment {

    private DownloadingViewModel mViewModel;

    private FragmentDownloadingBinding mBinding;

    public static DownloadingFragment newInstance() {
        return new DownloadingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloading, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DownloadingViewModel.class);
        mBinding.setDownloadingViewModel(mViewModel);
    }

}
