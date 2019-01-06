package com.dai.message.ui.music;

import com.dai.message.bean.BaseModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.network.api.MusicService;
import com.dai.message.network.retrofit.BaseApi;
import com.dai.message.network.retrofit.RetrofitClient;
import com.dai.message.repository.entity.CloudMusicEntity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

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

    /**
     * 获取服务端云盘音乐列表数据
     *
     * @param networkCallback NetworkCallback
     */
    public void getMusicList(NetworkCallback<BaseModel<List<CloudMusicEntity>>> networkCallback) {
        Call<BaseModel<List<CloudMusicEntity>>> call = RetrofitClient.getInstance()
                .create(BASE_URL, MusicService.class)
                .getCloudMusicList();

        ansyOperationHandle(call, networkCallback, "getMusicList", TAG);
    }

    /**
     * 下载音乐
     *
     * @param songName        歌曲名称
     * @param networkCallback NetworkCallback
     */
    public void downloadMusic(String songName, NetworkCallback<BaseModel<String>> networkCallback) {
        Call<BaseModel<String>> call = RetrofitClient.getInstance()
                .create(BASE_URL, MusicService.class)
                .downloadMusic(songName);

        ansyOperationHandle(call, networkCallback, "getMusicList", TAG);
    }


}
