package com.dai.message.ui.music.local;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.BaseModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.repository.MusicRepository;
import com.dai.message.ui.music.MusicApi;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LocalViewModel extends BaseAndroidViewModel {

    private static final String TAG = "LocalViewModel";


    private MutableLiveData<ArrayList<Music>> musicData = new MutableLiveData<>();


    private MusicApi musicApi;
    private MusicRepository musicRepository;

    public LocalViewModel(@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);

        musicApi = new MusicApi();
    }

    MutableLiveData<ArrayList<Music>> getMusicData() {
        musicRepository.getAllMusics(new LocalCallback<ArrayList<Music>>() {
            @Override
            public void onChangeData(ArrayList<Music> data) {
                musicData.postValue(data == null ? new ArrayList<Music>() : data);
            }
        });

        return musicData;
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
