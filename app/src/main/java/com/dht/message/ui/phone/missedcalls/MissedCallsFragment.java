package com.dht.message.ui.phone.missedcalls;

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

import com.dht.message.R;
import com.dht.message.adapter.CallRecordAdapter;
import com.dht.baselib.util.VerticalDecoration;
import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.message.repository.entity.AllCallsEntity;
import com.dht.message.databinding.FragmentMissedCallsBinding;

import java.util.ArrayList;

public class MissedCallsFragment extends BaseFragment {

    private MissedCallsViewModel mViewModel;

    private FragmentMissedCallsBinding mBinding;

    public static MissedCallsFragment newInstance() {
        return new MissedCallsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_missed_calls, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MissedCallsViewModel.class);
        mBinding.setMissedCallsViewModel(mViewModel);
        bindViews();
        // TODO: Use the ViewModel
    }

    @Override
    public void bindViews() {
        super.bindViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        final CallRecordAdapter callRecordAdapter = new CallRecordAdapter(recycleItemClickCallBack);
        mViewModel.getMissedCallsList().observe(this, new Observer<ArrayList<AllCallsEntity>>() {
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

            mViewModel.sendMessageToWeChat(allCallsEntity.getCallNumber());
        }
    };

}
