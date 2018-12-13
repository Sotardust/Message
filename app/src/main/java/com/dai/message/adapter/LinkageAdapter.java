package com.dai.message.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dai.message.base.BaseAdapter;
import com.dai.message.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemLinkageBinding;
import com.dai.message.databinding.RecycleItemNextLinkageBinding;

import java.util.List;

/**
 * created by Administrator on 2018/12/7 11:23
 */
public class LinkageAdapter extends BaseAdapter<String> {


    public LinkageAdapter(@NonNull RecycleItemClickCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setChangeList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    private List<List<String>> lists;
    private Context context;

    public void setChangeList(Context context, List<String> mList, List<List<String>> lists) {
        this.lists = lists;
        this.context = context;
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(@NonNull ViewGroup parent, int viewType) {
        RecycleItemLinkageBinding mBinding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.recycle_item_linkage,
                        parent, false);
        mBinding.setCallBack(callBack);
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.itemContent.setText(mList.get(position));
        for (String value : lists.get(position)) {
            final TextView textView = new TextView(context);
            textView.setText(value);
            textView.setBackgroundResource(R.drawable.bound_recycle_item);
            textView.setPadding(10, 15, 10, 15);
//            textView.
            textView.setOnClickListener(new View.OnClickListener() {
                boolean isClick = false;
                @Override
                public void onClick(View view) {
                    Log.d("dht", "NextLinkageAdapter onClick: ");
                    callBack.onItemClickListener(mList.get(position), position);
                    textView.setBackgroundResource(isClick ? R.drawable.bound_recycle_item : R.drawable.bound_recycle_item_blue);
                    isClick = !isClick;
                }
            });

            ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.linearlayout.addView(textView);
        }

        ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.setValue(mList.get(position));
//        StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.itemRecyclerView.setLayoutManager(staggered);
//        ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.itemRecyclerView.setAdapter(adapters.get(position));
        ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.setIndex(position);
    }
}
