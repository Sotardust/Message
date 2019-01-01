package com.dai.message.network.api;

import com.dai.message.bean.BaseModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MusicService {


    /**
     * 上传音乐文件
     *
     * @param body MultipartBody
     */
    @POST("uploadmusic")
    Call<BaseModel<ArrayList<String>>> uploadFile(@Body MultipartBody body);
}
