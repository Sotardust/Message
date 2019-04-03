package com.dai.message.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dai.message.R;
import com.dai.message.adapter.util.ViewHolder;
import com.dht.commonlib.base.BaseAdapter;
import com.dht.commonlib.callback.RecycleItemClickCallBack;
import com.dai.message.databinding.RecycleItemLinkageBinding;

import java.util.List;

/**
 * created by Administrator on 2018/12/7 11:23
 */
public class LinkageAdapter extends BaseAdapter<String> {


    public LinkageAdapter(@NonNull RecycleItemClickCallBack<String> callBack) {
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
        return new ViewHolder<>(mBinding);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder<RecycleItemLinkageBinding>) holder).mBinding.itemContent.setText(mList.get(position));
        setFlowLayout((ViewHolder<RecycleItemLinkageBinding>) holder, lists.get(position), position);

    }

    @SuppressWarnings("unchecked")
    private void setFlowLayout(ViewHolder<RecycleItemLinkageBinding> viewHolder, final List<String> list, final int position) {
        for (final String value : list) {
            final TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.flowlayout_textview, viewHolder.mBinding.flowLayout, false);
            textView.setText(value);
            textView.setOnClickListener(new View.OnClickListener() {
                boolean isClick = true;

                @Override
                public void onClick(View view) {
                    Log.d("dht", "onClick: index = " + list.indexOf(value) + ", value = " + value);
                    isClick = !isClick;
                    textView.setBackgroundResource(isClick ? R.drawable.bound_recycle_item : R.drawable.bound_recycle_item_blue);
                    callBack.onItemClickListener(value, position);
                }
            });
            viewHolder.mBinding.flowLayout.addView(textView);
            viewHolder.mBinding.flowLayout.setShowLines(number);
        }
    }

    private int number = -1;

    public void setShowLines(int number) {
        this.number = number;
        notifyDataSetChanged();
    }
}
