package com.dht.music.ui.local;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.NetworkCallback;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.api.MusicApi;
import com.dht.music.repository.MusicRepository;
import com.dht.music.repository.RecentPlayRepository;
import com.dht.network.BaseModel;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class LocalViewModel extends BaseAndroidViewModel {

    private static final String TAG = "LocalViewModel";


    private MutableLiveData<ArrayList<MusicBean>> musicData = new MutableLiveData<>();


    private MusicApi musicApi;
    private MusicRepository musicRepository;
    private RecentPlayRepository playRepository;

    public LocalViewModel(@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
        playRepository = new RecentPlayRepository(application);

        musicApi = new MusicApi();
    }

    MutableLiveData<ArrayList<MusicBean>> getMusicData() {
        musicRepository.getAllMusics(new LocalCallback<ArrayList<MusicBean>>() {
            @Override
            public void onChangeData(ArrayList<MusicBean> data) {
                musicData.postValue(data == null ? new ArrayList<MusicBean>() : data);
            }
        });

        return musicData;
    }


    /**
     * 插入或者更新播放历史记录
     *
     * @param musicBean MusicBean
     */
    public void insertOrUpdate(MusicBean musicBean) {
        playRepository.insertOrUpdate(musicBean);
    }

    /**
     * 上传音乐文件
     *
     * @param fileList        文件集合
     * @param networkCallback 回调接口
     */
    public void uploadFiles(List<File> fileList, NetworkCallback<BaseModel<ArrayList<String>>> networkCallback) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multiple/form-data"), file);
            builder.addFormDataPart("file", URLEncoder.encode(file.getName()), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        musicApi.uploadFile(builder.build(), networkCallback);
    }
}
