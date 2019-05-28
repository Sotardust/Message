package com.dht.music.api;

import com.dht.baselib.base.BaseApi;
import com.dht.baselib.callback.NetworkCallback;
import com.dht.databaselib.bean.music.CloudMusicBean;
import com.dht.network.BaseModel;
import com.dht.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * @author Administrator
 */
public class MusicApi extends BaseApi {

    private static final String TAG = "MusicApi";

    /**
     * 上传音乐文件
     *
     * @param body            MultipartBody
     * @param networkCallback 回调接口
     */
    public void uploadFile (MultipartBody body, NetworkCallback<BaseModel<ArrayList<String>>> networkCallback) {
        Call<BaseModel<ArrayList<String>>> call = RetrofitClient.getInstance()
                .create(BaseApi.BASE_URL, MusicService.class)
                .uploadFile(body);

        ansyOperationHandle(call, networkCallback, "uploadFile", TAG);

    }

    /**
     * 获取服务端云盘音乐列表数据
     *
     * @param networkCallback NetworkCallback
     */
    public void getMusicList (NetworkCallback<BaseModel<List<CloudMusicBean>>> networkCallback) {
        Call<BaseModel<List<CloudMusicBean>>> call = RetrofitClient.getInstance()
                .create(BaseApi.BASE_URL, MusicService.class)
                .getCloudMusicList();

        ansyOperationHandle(call, networkCallback, "getMusicList", TAG);
    }

    /**
     * 下载音乐
     *
     * @param songName        歌曲名称
     * @param networkCallback NetworkCallback
     */
    public void downloadMusic (String songName, NetworkCallback<BaseModel<String>> networkCallback) {
        Call<BaseModel<String>> call = RetrofitClient.getInstance()
                .create(BaseApi.BASE_URL, MusicService.class)
                .downloadMusic(songName);

        ansyOperationHandle(call, networkCallback, "downloadMusic", TAG);
    }


}
