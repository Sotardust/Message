package com.dht.message.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.dht.message.callback.OnPageChangerCallback;
import com.dht.message.databinding.MainFragmentBinding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.message.R;
import com.dht.baselib.base.BaseFragmentPageAdapter;
import com.dht.baselib.base.BaseFragment;
import com.dht.message.ui.other.SecondaryLinkageFragment;
import com.dht.message.ui.phone.allcalls.AllCallsFragment;
import com.dht.message.ui.phone.answered.AnsweredFragment;
import com.dht.message.ui.phone.dial.DialFragment;
import com.dht.message.ui.phone.missedcalls.MissedCallsFragment;
import com.dht.message.ui.phone.refuse.RefuseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private MainViewModel mViewModel;

    private String[] titles = {"未接来电", "已接来电", "拨打电话", "拒接来电", "全部来电", "二级联动"};

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
        mFragmentList.add(MissedCallsFragment.newInstance());
        mFragmentList.add(AnsweredFragment.newInstance());
        mFragmentList.add(DialFragment.newInstance());
        mFragmentList.add(RefuseFragment.newInstance());
        mFragmentList.add(AllCallsFragment.newInstance());
        mFragmentList.add(SecondaryLinkageFragment.newInstance());

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
