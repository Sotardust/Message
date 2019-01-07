package com.dai.message.ui.login;

import com.dai.message.bean.BaseModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.network.service.LoginService;
import com.dai.message.network.retrofit.BaseApi;
import com.dai.message.network.retrofit.RetrofitClient;

import retrofit2.Call;

public class LoginApi extends BaseApi {


    private static final String TAG = "BaseModel";

//    // 使用本机iP地址 不能使用 127.0.0.1（虚拟机把其作为自身IP）
//    private static final String BASE_URL = "http://192.168.199.192:8080/message/";

    /**
     * 登录
     *
     * @param name            用户
     * @param password        密码
     * @param networkCallback 回调接口
     */
    public void logon(String name, String password, NetworkCallback<BaseModel<String>> networkCallback) {
        Call<BaseModel<String>> call = RetrofitClient.getInstance()
                .create(BASE_URL, LoginService.class)
                .login(name, password);
        ansyOperationHandle(call, networkCallback, "logon", TAG);
    }

    /**
     * 注册账号
     *
     * @param name            用户名
     * @param password        密码
     * @param registerTime    注册时间
     * @param networkCallback 回调接口
     */
    public void register(String name, String password, String registerTime, NetworkCallback<BaseModel<String>> networkCallback) {
        Call<BaseModel<String>> call = RetrofitClient.getInstance()
                .create(BASE_URL, LoginService.class)
                .register(name, password, registerTime);
        ansyOperationHandle(call, networkCallback, "logon", TAG);
    }


}
