package com.dai.message.ui.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.dai.message.bean.BaseModel;
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
    public void logon(String name, String password, NetworkCallback<BaseModel<String>> loginCallBack) {

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

    private NetworkCallback<BaseModel<String>> registerCallBack = new NetworkCallback<BaseModel<String>>() {
        @Override
        public void onChangeData(BaseModel<String> model) {
            if (model == null) {
                ToastUtil.toastCustom(application.getApplicationContext(), "网络超时", 200);
                return;
            }
            ToastUtil.toastCustom(application.getApplicationContext(), model.msg, 200);
        }
    };

}
