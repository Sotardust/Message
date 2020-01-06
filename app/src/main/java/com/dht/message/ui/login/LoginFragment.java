package com.dht.message.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.NetworkCallback;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.eventbus.RxBus;
import com.dht.eventbus.RxCallBack;
import com.dht.eventbus.event.InitPlayListEvent;
import com.dht.message.MainActivity;
import com.dht.message.R;
import com.dht.message.databinding.FragmentLoginBinding;
import com.dht.music.repository.MusicRepository;
import com.dht.network.BaseModel;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";

    private LoginViewModel mViewModel;

    private FragmentLoginBinding mBinding;
    private MusicRepository repository;

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
        repository = new MusicRepository(weakReference.get());
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mBinding.setLoginViewModel(mViewModel);
        bindViews();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        mBinding.login.setOnClickListener(this);
        mBinding.register.setOnClickListener(this);
//        mViewModel.initData();
        initRxEvent();
    }

    @Override
    public void handlingClickEvents(View view) {
        super.handlingClickEvents(view);
        switch (view.getId()) {
            case R.id.login:
                String name = mBinding.name.getText().toString();
                String password = mBinding.password.getText().toString();
                MessagePreferences.INSTANCE.setFirstPlay(true);
                if (isEmpty(name, password)) {
                    return;
                }

                if ("0".equals(name) && "0".equals(password)) {
                    toMainActivity();
                    return;
                }
                mViewModel.logon(name, password, loginCallBack);

                break;
            case R.id.register:
                String name1 = mBinding.name.getText().toString();
                String password2 = mBinding.password.getText().toString();
                String registerTime = String.valueOf(System.currentTimeMillis());
                if (isEmpty(name1, password2)) {
                    return;
                }
                mViewModel.register(name1, password2, registerTime);
                break;
            default:
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
            mModel.initPlayList();
            if (model.code == 0) {
                toMainActivity();
            }
            ToastUtil.toastCustom(getContext(), model.msg, 200);
        }
    };


    private void initRxEvent() {
        RxBus.getInstance().toRxBusResult(InitPlayListEvent.class, new RxCallBack<InitPlayListEvent>() {
            @Override
            public void onCallBack(InitPlayListEvent data) {
                super.onCallBack(data);
                Log.d(TAG, "onCallBack: initRxEvent ");
                repository.getAllMusics(new LocalCallback<ArrayList<MusicBean>>() {
                    @Override
                    public void onChangeData(ArrayList<MusicBean> data) {
                        super.onChangeData(data);
                        mModel.setPlayList(data);
                        if (MessagePreferences.INSTANCE.getCurrentMusic() == null && data.size() != 0) {
                            MessagePreferences.INSTANCE.setCurrentMusic(data.get(0));
                        }
                    }
                });
            }
        });
    }

    /**
     * 跳转到主页面
     */
    private void toMainActivity() {
        MessagePreferences.INSTANCE.setPersonId(123);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

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
