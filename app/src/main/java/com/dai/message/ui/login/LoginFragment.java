package com.dai.message.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.message.MainActivity;
import com.dai.message.R;
import com.dai.message.base.BaseFragment;
import com.dai.message.bean.BaseModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.databinding.FragmentLoginBinding;
import com.dai.message.util.ToastUtil;

public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";

    private LoginViewModel mViewModel;

    private FragmentLoginBinding mBinding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);


        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mBinding.setLoginViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        mBinding.login.setOnClickListener(this);
        mBinding.register.setOnClickListener(this);
    }


    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        switch (view.getId()) {
            case R.id.login:
                String name = mBinding.name.getText().toString();
                String password = mBinding.password.getText().toString();
                if (isEmpty(name, password)) return;
                mViewModel.logon(name, password, loginCallBack);

                break;
            case R.id.register:


                String name1 = mBinding.name.getText().toString();
                String password2 = mBinding.password.getText().toString();
                String registerTime = String.valueOf(System.currentTimeMillis());
                if (isEmpty(name1, password2)) return;
                mViewModel.register(name1, password2, registerTime);
                break;
        }
    }

    private NetworkCallback<BaseModel<String>> loginCallBack = new NetworkCallback<BaseModel<String>>() {
        @Override
        public void onChangeData(BaseModel<String> model) {
            if (model == null) {
                ToastUtil.toastCustom(getContext(), "网络超时", 200);
                return;
            }
            if (model.code == 0) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                if (getActivity() != null) getActivity().finish();
            }
            ToastUtil.toastCustom(getContext(), model.msg, 200);
        }
    };

    /**
     * 判断是否为空
     *
     * @param name     用户名
     * @param password 密码
     * @return 布尔型
     */
    private boolean isEmpty(String name, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            ToastUtil.toastCustom(getContext(), "用户名或密码不能为空！", 200);
            return true;
        }
        return false;
    }

}
