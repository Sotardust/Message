package com.dht.music.ui.cloud;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dht.baselib.callback.NetworkCallback;
import com.dht.baselib.callback.ObservableCallback;
import com.dht.baselib.callback.ObserverCallback;
import com.dht.baselib.util.file.FileManager;
import com.dht.baselib.util.file.PathUtil;
import com.dht.databaselib.bean.music.CloudMusicBean;
import com.dht.music.api.MusicApi;
import com.dht.network.BaseModel;
import com.dht.network.HttpStatusCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.ObservableEmitter;


public class CloudDiskViewModel extends ViewModel {

    private static final String TAG = "CloudDiskViewModel";

    private MusicApi musicApi;


    private static String fileName = null;


    public CloudDiskViewModel () {
        musicApi = new MusicApi();
    }

    public void getMusicList (NetworkCallback<BaseModel<List<CloudMusicBean>>> callback) {
        musicApi.getMusicList(callback);
    }

    public void downloadMusic (String songName) {
        fileName = songName;
        musicApi.downloadMusic(URLEncoder.encode(songName), networkCallback);
    }

    private NetworkCallback<BaseModel<String>> networkCallback = new NetworkCallback<BaseModel<String>>() {
        @Override
        public void onChangeData (final BaseModel<String> data) {
            if (data == null) {
                return;
            }
            if (data.code != HttpStatusCode.CODE_100) {
                return;
            }
            musicApi.ansyObtainData(new ObservableCallback<String>() {
                @Override
                public void subscribe (ObservableEmitter<String> emitter) throws Exception {
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
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    }
                }
            }, new ObserverCallback<String>() {
                @Override
                public void onNext (String s) {
                    super.onNext(s);
                    Log.d(TAG, "onNext: s= " + s);
                }

                @Override
                public void onError (Throwable e) {
                    super.onError(e);
                    e.printStackTrace();
                    Log.e(TAG, "onError: e" + e);
                }
            });


        }
    };
}
