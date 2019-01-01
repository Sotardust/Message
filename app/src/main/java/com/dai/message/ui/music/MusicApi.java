package com.dai.message.ui.music;

import android.util.Log;

import com.dai.message.bean.BaseModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.network.api.MusicService;
import com.dai.message.network.retrofit.BaseApi;
import com.dai.message.network.retrofit.RetrofitClient;

import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import retrofit2.Call;

public class MusicApi extends BaseApi {

    private static final String TAG = "MusicApi";

    /**
     * 上传音乐文件
     *
     * @param body            MultipartBody
     * @param networkCallback 回调接口
     */
    public void uploadFile(MultipartBody body, NetworkCallback<BaseModel<ArrayList<String>>> networkCallback) {
        Call<BaseModel<ArrayList<String>>> call = RetrofitClient.getInstance()
                .create(BASE_URL, MusicService.class)
                .uploadFile(body);

        ansyOperationHandle(call, networkCallback, "uploadFile", TAG);

    }
}
