package com.dai.message.ui.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.dai.message.MainActivity;
import com.dai.message.bean.model.LoginModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.util.ToastUtil;

public class LoginViewModel extends AndroidViewModel {


    private static final String TAG = "LoginViewModel";

    private Application application;

    private LoginApi loginApi;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        loginApi = new LoginApi();

    }


    /**
     * 登录
     *
     * @param name          用户
     * @param password      密码
     * @param loginCallBack 登录回调接口
     */
    public void logon(String name, String password, NetworkCallback<LoginModel> loginCallBack) {

        loginApi.logon(name, password, loginCallBack);
    }

    /**
     * 注册账号
     *
     * @param name         用户名
     * @param password     密码
     * @param registerTime 注册时间
     */
    public void register(String name, String password, String registerTime) {
        loginApi.register(name, password, registerTime, registerCallBack);
    }

    private NetworkCallback<LoginModel> registerCallBack = new NetworkCallback<LoginModel>() {
        @Override
        public void onChangeData(LoginModel model) {
            if (model == null) {
                ToastUtil.toastCustom(application.getApplicationContext(), "网络超时", 200);
                return;
            }
            ToastUtil.toastCustom(application.getApplicationContext(), model.msg, 200);
        }
    };

}
