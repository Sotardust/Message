package com.dai.message.ui.music.cloud;

import android.util.Log;

import com.dai.message.base.BaseViewModel;
import com.dai.message.bean.BaseModel;
import com.dai.message.callback.NetworkCallback;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.callback.ObserverCallback;
import com.dai.message.repository.entity.CloudMusicEntity;
import com.dai.message.ui.music.MusicApi;
import com.dai.message.util.file.FileManager;
import com.dai.message.util.file.PathUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.ObservableEmitter;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class CloudDiskViewModel extends BaseViewModel {

    private static final String TAG = "CloudDiskViewModel";

    private MusicApi musicApi;


    private static String fileName = null;


    public CloudDiskViewModel() {
        musicApi = new MusicApi();
    }

    public void getMusicList(NetworkCallback<BaseModel<List<CloudMusicEntity>>> callback) {
        musicApi.getMusicList(callback);
    }

    public void downloadMusic(String songName) {
        fileName = songName;
        musicApi.downloadMusic(URLEncoder.encode(songName), networkCallback);
    }

    private NetworkCallback<BaseModel<String>> networkCallback = new NetworkCallback<BaseModel<String>>() {
        @Override
        public void onChangeData(final BaseModel<String> data) {
            if (data == null) return;
            if (data.code != 0) return;
            musicApi.ansyObtainData(new ObservableCallback<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    super.subscribe(emitter);
                    Log.d(TAG, "subscribe: file = " + fileName);
                    String path = PathUtil.MUSIC_PATH + fileName;
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(FileManager.getInstance().createNewFile(path));
                        fileOutputStream.write(data.result.getBytes());
                        fileOutputStream.flush();//将内容一次性写入文件
                        emitter.onNext("写入成功");
                    } catch (IOException e) {
                        Log.d(TAG, "run() returned: " + e);
                        e.printStackTrace();
                        emitter.onNext("写入失败");
                    } finally {
                        if (fileOutputStream != null)
                            fileOutputStream.close();
                    }
                }
            }, new ObserverCallback<String>() {
                @Override
                public void onNext(String s) {
                    super.onNext(s);
                    Log.d(TAG, "onNext: s= " + s);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    e.printStackTrace();
                    Log.e(TAG, "onError: e" + e);
                }
            });


        }
    };
}
