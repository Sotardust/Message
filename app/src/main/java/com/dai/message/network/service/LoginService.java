package com.dai.message.network.service;


import com.dht.network.BaseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    /**
     * 登录接口
     *
     * @param name     用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("login")
    Call<BaseModel<String>> login(@Field("name") String name, @Field("password") String password);

    /**
     * 注册用户接口
     *
     * @param name         用户名
     * @param password     密码
     * @param registerTime 注册时间
     */
    @FormUrlEncoded
    @POST("register")
    Call<BaseModel<String>> register(@Field("name") String name, @Field("password") String password, @Field("register_time") String registerTime);
}
