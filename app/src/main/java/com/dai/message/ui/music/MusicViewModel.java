package com.dai.message.ui.music;

import android.arch.lifecycle.MutableLiveData;
import android.os.Environment;
import android.util.Log;

import com.dai.message.base.BaseViewModel;
import com.dai.message.util.LogUtil;

import java.io.File;
import java.util.ArrayList;

public class MusicViewModel extends BaseViewModel {


    private static final String TAG = "MusicViewModel";

    private ArrayList<File> filePaths = new ArrayList<>();

    private MutableLiveData<Integer> liveData = new MutableLiveData<>();

    public MusicViewModel() {
        initData();
    }

    public MutableLiveData<Integer> getliveData() {
        return liveData;
    }


    /**
     * 初始化数据
     */
    private void initData() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Music";
        File file = new File(path);
        if (!file.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", path + "路径不存在");
            Log.d(TAG, "searchSong: " + path + "路径不存在");
            return;
        }
        if (filePaths.size() != 0) filePaths.clear();
        searchMusicFile(file.getPath());
    }

    /**
     * 遍历查找歌曲文件
     *
     * @param path 路径
     */
    private void searchMusicFile(String path) {
        File file1 = new File(path);
        File[] files = file1.listFiles();
        Log.d(TAG, "searchSongFile: files.length = " + files.length);
        for (File file : files) {
            if (file.isFile() && (file.getName().contains(".mp3") && !file.getName().contains(".mp3.") || file.getName().contains(".flac"))) {
                Log.d(TAG, "findSong: " + file.getPath());
                filePaths.add(file);
            } else if (file.isDirectory())
                searchMusicFile(file.getPath());
        }
        liveData.postValue(filePaths.size());
    }

}
