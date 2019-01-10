package com.dai.message.network.service;

import com.dai.message.bean.BaseModel;
import com.dai.message.repository.entity.CloudMusicEntity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MusicService {


    /**
     * 上传音乐文件
     *
     * @param body MultipartBody
     */
    @POST("uploadMusic")
    Call<BaseModel<ArrayList<String>>> uploadFile(@Body MultipartBody body);

    /**
     * 获取服务端云盘音乐列表数据
     *
     * @return 云盘音乐数据集合
     */
    @GET("getCloudMusic")
    Call<BaseModel<List<CloudMusicEntity>>> getCloudMusicList();

    /**
     * 下载音乐文件
     *
     * @param songName 歌曲名称
     * @return 文件以以字符类型返回
     */
    @FormUrlEncoded
    @POST("downloadMusic")
    Call<BaseModel<String>> downloadMusic(@Field("songName") String songName);

}
