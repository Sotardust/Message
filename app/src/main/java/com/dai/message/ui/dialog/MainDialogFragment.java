package com.dai.message.ui.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.R;
import com.dai.message.base.BaseDialogFragment;
import com.dai.message.databinding.FragmentMainDialogBinding;

public class MainDialogFragment extends BaseDialogFragment {

    private MainDialogViewModel mViewModel;
    private FragmentMainDialogBinding mBinding;

    public static MainDialogFragment newInstance() {
        return new MainDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setWindowParams();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_dialog, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainDialogViewModel.class);
        mBinding.setMainDialogViewModel(mViewModel);
    }

}
