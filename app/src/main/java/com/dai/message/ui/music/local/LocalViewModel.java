package com.dai.message.ui.music.local;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.dai.message.BuildConfig;
import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.BaseModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.ui.music.MusicApi;
import com.dai.message.util.LogUtil;
import com.dai.message.util.file.FileType;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LocalViewModel extends BaseAndroidViewModel {

    private static final String TAG = "LocalViewModel";

    private ArrayList<File> filePaths = new ArrayList<>();

    private Application application;

    private MutableLiveData<ArrayList<File>> filesListData = new MutableLiveData<>();


    private MusicApi musicApi;

    public LocalViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        traversalSong();
        musicApi = new MusicApi();
    }

    MutableLiveData<ArrayList<File>> getFilesListData() {
        return filesListData;
    }


    private void traversalSong() {

        //模拟器地址
//        String path = Environment.getExternalStorageDirectory() + File.separator + "Music";
        //手机地址
        String path = Environment.getExternalStorageDirectory() + File.separator + "Music";
        File file = new File(path);
        if (!file.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", path + "路径不存在");
            Log.d(TAG, "searchSong: " + path + "路径不存在");
            return;
        }
        if (!filePaths.isEmpty()) filePaths.clear();
        searchSongFile(path);
    }


    /**
     * 遍历查找歌曲文件
     *
     * @param path 路径
     */
    private void searchSongFile(String path) {
        File file1 = new File(path);
        File[] files = file1.listFiles();
        Log.d(TAG, "searchSongFile: files.length = " + files.length);
        for (File file : files) {
            if (file.isFile() && (file.getName().contains(".mp3") && !file.getName().contains(".mp3.") || file.getName().contains(".flac"))) {
                Log.d(TAG, "findSong: " + file.getPath());
                filePaths.add(file);
            } else if (file.isDirectory())
                searchSongFile(file.getPath());
        }
        filesListData.postValue(filePaths);
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
