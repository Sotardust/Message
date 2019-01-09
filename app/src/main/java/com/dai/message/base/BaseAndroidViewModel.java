package com.dai.message.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * 含有Application上下文的ViewModel
 * <p>
 * created by dht on 2018/6/29 14:48
 */
public class BaseAndroidViewModel extends AndroidViewModel {

    protected Application application;

    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    /**
     * 解析歌曲名称
     *
     * @param path 路径
     * @return 歌名
     */
    public String parseSongName(String path) {
        if (path == null) return null;
        String[] strings = path.split("-");
        String[] songName = strings[strings.length - 1].split("\\.");
        return songName[0];
    }

    /**
     * 解析歌手名
     *
     * @param path 路径
     * @return 歌手名
     */
    public String parseAuthor(String path) {
        if (path == null) return null;
        String[] username = path.split("-");
        return username[0];
    }
}
