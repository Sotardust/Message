package com.dai.message.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
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
import com.dai.message.callback.OnPageChangerCallback;
import com.dai.message.databinding.FragmentHomeBinding;
import com.dai.message.ui.music.MusicFragment;
import com.dai.message.ui.other.SecondaryLinkageFragment;
import com.dai.message.ui.phone.allcalls.AllCallsFragment;
import com.dai.message.ui.phone.answered.AnsweredFragment;
import com.dai.message.ui.phone.dial.DialFragment;
import com.dai.message.ui.phone.missedcalls.MissedCallsFragment;
import com.dai.message.ui.phone.refuse.RefuseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private HomeViewModel mViewModel;
    private FragmentHomeBinding mBinding;

    private String[] titles = {"音乐", "新闻", "小说", "我的", "电话", "其他"};

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
        mBinding.setHomeViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(MusicFragment.newInstance());
        mFragmentList.add(MusicFragment.newInstance());
        mFragmentList.add(MusicFragment.newInstance());
        mFragmentList.add(MusicFragment.newInstance());
        mFragmentList.add(MusicFragment.newInstance());
        mFragmentList.add(MusicFragment.newInstance());

        mBinding.baseViewPager.setAdapter(new BaseFragmentPageAdapter(getChildFragmentManager(), mFragmentList, titles));
        mBinding.baseViewPager.setCurrentItem(0);
        mBinding.baseViewPager.setOffscreenPageLimit(6);
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
