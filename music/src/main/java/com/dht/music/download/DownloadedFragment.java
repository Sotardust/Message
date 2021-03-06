package com.dht.music.download;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseFragment;
import com.dht.music.R;
import com.dht.music.databinding.FragmentDownloadedBinding;

/**
 * @author Administrator
 */
public class DownloadedFragment extends BaseFragment {

    private DownloadedViewModel mViewModel;

    private FragmentDownloadedBinding mBinding;

    public static DownloadedFragment newInstance () {
        return new DownloadedFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloaded, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DownloadedViewModel.class);
        mBinding.setDownloadedViewModel(mViewModel);
    }

}
