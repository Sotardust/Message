package com.dai.message.ui.main.allcalls;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.adapter.CallRecordAdapter;
import com.dai.message.adapter.util.VerticalDecoration;
import com.dai.message.base.BaseFragment;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.FragmentAllCallsBinding;
import com.dai.message.repository.entity.AllCallsEntity;

import java.util.ArrayList;

public class AllCallsFragment extends BaseFragment {

    private AllCallsViewModel mViewModel;

    private FragmentAllCallsBinding mBinding;

    public static AllCallsFragment newInstance() {
        return new AllCallsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_calls, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AllCallsViewModel.class);
        mBinding.setAllCallsViewModel(mViewModel);
        bindViews();
        // TODO: Use the ViewModel
    }

    @Override
    public void bindViews() {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        final CallRecordAdapter callRecordAdapter = new CallRecordAdapter(recycleItemClickCallBack);
        mViewModel.getAllCallsList().observe(this, new Observer<ArrayList<AllCallsEntity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<AllCallsEntity> allCallEntities) {
                callRecordAdapter.setChangeList(allCallEntities);
            }
        });
        mBinding.recyclerView.setAdapter(callRecordAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(2));
    }

    private RecycleItemClickCallBack<AllCallsEntity> recycleItemClickCallBack = new RecycleItemClickCallBack<AllCallsEntity>() {
        @Override
        public void onItemClickListener(AllCallsEntity allCallsEntity, int position) {
            super.onItemClickListener(allCallsEntity, position);
        }
    };
}
