package com.dht.message.ui.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.message.R;
import com.dht.message.databinding.FragmentMainDialogBinding;
import com.dht.baselib.base.BaseDialogFragment;
import com.dht.baselib.callback.LocalCallback;

/**
 * @author Administrator
 */
public class MainDialogFragment extends BaseDialogFragment {

    private MainDialogViewModel mViewModel;
    private FragmentMainDialogBinding mBinding;
    private LocalCallback<String> localCallback;

    public static MainDialogFragment newInstance() {
        return new MainDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setCenterWindowParams();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_dialog, container, false);
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainDialogViewModel.class);
        mBinding.setMainDialogViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();

        mBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                localCallback.onChangeData();
            }
        });
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置确定按钮的回调接口
     *
     * @param localCallback 回调接口
     */
    public void setOkCallBack(final LocalCallback<String> localCallback) {
        this.localCallback = localCallback;
    }
}
