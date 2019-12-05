package com.dht.message.ui.other;

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
import android.widget.TextView;

import com.dht.message.R;
import com.dht.message.adapter.LinkageAdapter;
import com.dht.message.adapter.NextLinkageAdapter;
import com.dht.baselib.util.VerticalDecoration;
import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.RecycleItemClickCallBack;
import com.dht.message.databinding.FragmentSecondaryLinkageBinding;
import com.dht.message.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SecondaryLinkageFragment extends BaseFragment {

    private SecondaryLinkageViewModel mViewModel;
    private FragmentSecondaryLinkageBinding mBinding;

    public static SecondaryLinkageFragment newInstance() {
        return new SecondaryLinkageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_secondary_linkage, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory mViewModelFactory = ViewModelFactory.getInstance(weakReference.get());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SecondaryLinkageViewModel.class);
        mBinding.setSecondaryLinkageViewModel(mViewModel);
        bindViews();
        // TODO: Use the ViewModel
    }

    ArrayList<NextLinkageAdapter> adapters = new ArrayList<>();

    @Override
    public void bindViews() {
        super.bindViews();

//        mBinding.flowLayout.set

        ArrayList<String> list = new ArrayList<>();
        final ArrayList<String> nextlist1 = new ArrayList<>();
        ArrayList<String> nextlist2 = new ArrayList<>();
        ArrayList<String> nextlist3 = new ArrayList<>();
        ArrayList<String> nextlist4 = new ArrayList<>();
        ArrayList<String> nextlist5 = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("测试 " + i);

        }
        for (int i = 0; i < 15; i++) {
            nextlist2.add("测试1下 " + i);
            nextlist3.add("测试2下 " + i);
            nextlist4.add("测试3下 " + i);
            nextlist5.add("测试4下 " + i);
        }
        nextlist1.add("测试一二三四五六");
        nextlist1.add("测试yi");
        nextlist1.add("测试ersan");
        nextlist1.add("测试一");
        nextlist1.add("测试一二三四五六");
        nextlist1.add("测二三四五六1");
        nextlist1.add("测试一二测试一二三四五六2");

        nextlist1.add("测试测试测");
        nextlist1.add("说什么好呢");
        nextlist1.add("遇见");
        nextlist1.add("光年之外");
        nextlist1.add("光年之外");

        nextlist1.add("我有我爱我");
        nextlist1.add("红");
        nextlist1.add("可能否");
        nextlist1.add("空空如也");
        nextlist1.add("狠人大帝");

        for (final String value : nextlist1) {
            final TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flowlayout_textview, mBinding.flowLayout, false);
            textView.setText(value);

            textView.setOnClickListener(new View.OnClickListener() {
                boolean isClick = false;

                @Override
                public void onClick(View view) {
                    Log.d("dht", "onClick: index = " + nextlist1.indexOf(value) + ", value = " + value);
                    textView.setBackgroundResource(isClick ? R.drawable.bound_recycle_item : R.drawable.bound_recycle_item_blue);
                    isClick = !isClick;
                }
            });
            mBinding.flowLayout.addView(textView);
        }
        lists.add(nextlist1);
        lists.add(nextlist2);
        lists.add(nextlist3);
        lists.add(nextlist4);
        lists.add(nextlist5);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linkageAdapter = new LinkageAdapter(recycleItemClickCallBack);

        NextLinkageAdapter adapter1 = new NextLinkageAdapter(nextRecycleItemClickCallBack);
        adapter1.setChangeList(nextlist1);
        NextLinkageAdapter adapter2 = new NextLinkageAdapter(nextRecycleItemClickCallBack);
        adapter2.setChangeList(nextlist2);
        NextLinkageAdapter adapter3 = new NextLinkageAdapter(nextRecycleItemClickCallBack);
        adapter3.setChangeList(nextlist3);
        NextLinkageAdapter adapter4 = new NextLinkageAdapter(nextRecycleItemClickCallBack);
        adapter4.setChangeList(nextlist4);
        NextLinkageAdapter adapter5 = new NextLinkageAdapter(nextRecycleItemClickCallBack);
        adapter5.setChangeList(nextlist5);

        adapters.add(adapter1);
        adapters.add(adapter2);
        adapters.add(adapter3);
        adapters.add(adapter4);
        adapters.add(adapter5);

        linkageAdapter.setChangeList(getContext(), list, lists);
        mBinding.recyclerView.setAdapter(linkageAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.addItemDecoration(new VerticalDecoration(2));
    }

    private LinkageAdapter linkageAdapter;
    private RecycleItemClickCallBack<String> recycleItemClickCallBack = new RecycleItemClickCallBack<String>() {
        boolean isClick = true;

        @Override
        public void onItemClickListener(String string, int position) {
            super.onItemClickListener(string, position);
            isClick = !isClick;
            linkageAdapter.setShowLines(1);
//            adapters.get(position).setShowAll(isClick);

        }
    };
    private RecycleItemClickCallBack<String> nextRecycleItemClickCallBack = new RecycleItemClickCallBack<String>() {
        @Override
        public void onItemClickListener(String string, int position) {
            super.onItemClickListener(string, position);
            Log.d("dht", "next onItemClickListener string: " + string + ", position = " + position);
        }
    };
}
