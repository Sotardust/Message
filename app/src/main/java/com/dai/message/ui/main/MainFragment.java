package com.dai.message.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.dai.message.callback.OnPageChangerCallback;
import com.dai.message.databinding.MainFragmentBinding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.BaseFragmentPageAdapter;
import com.dai.message.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private MainViewModel mViewModel;

    private String[] titles = {"全部来电", "未接来电", "已接来电", "待补充"};

    private MainFragmentBinding mBinding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        bindViews();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mBinding.setMainViewModel(mViewModel);
        // TODO: Use the ViewModel
    }

    @Override
    public void bindViews() {
        super.bindViews();
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(AllCallsFragment.newInstance());
        mFragmentList.add(AllCallsFragment.newInstance());
        mFragmentList.add(AllCallsFragment.newInstance());
        mFragmentList.add(AllCallsFragment.newInstance());
//        mFragmentList.add(mineFragment);
//        mFragmentList.add(transceiverFragment);
        mBinding.baseViewPager.setAdapter(new BaseFragmentPageAdapter(getChildFragmentManager(), mFragmentList, titles));
        mBinding.baseViewPager.setCurrentItem(0);
        mBinding.baseViewPager.setOffscreenPageLimit(2);
        mBinding.tabLayout.setupWithViewPager(mBinding.baseViewPager);
        mBinding.baseViewPager.addOnPageChangeListener(new OnPageChangerCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mBinding.baseViewPager.setCurrentItem(position);
            }
        });
    }
}
