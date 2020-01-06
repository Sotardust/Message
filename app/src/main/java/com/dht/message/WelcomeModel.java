package com.dht.message;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.util.LogUtil;
import com.dht.baselib.util.ParseUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.repository.MusicRepository;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Administrator
 */
public class WelcomeModel extends BaseAndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private MusicRepository musicRepository;
    private ArrayList<File> filePaths = new ArrayList<>();

    public WelcomeModel (@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
    }

    /**
     * 初始化数据
     */
    public void initDatabaseData (LocalCallback<String> pathCallback, LocalCallback<String> localCallback) {
        String neteasePath = Environment.getExternalStorageDirectory() + File.separator + "netease";
        String localPath = Environment.getExternalStorageDirectory() + File.separator + "music";
        File neteaseFile = new File(neteasePath);
        File localFile = new File(localPath);
        if (filePaths.size() != 0) {
            filePaths.clear();
        }
        if (!neteaseFile.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", neteasePath + "路径不存在");
            Log.d(TAG, "searchSong: " + neteasePath + "路径不存在");
        } else {
            traversingMusicFile(neteaseFile.getPath(), pathCallback);
        }
        if (!localFile.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", localFile + "路径不存在");
            Log.d(TAG, "searchSong: " + localFile + "路径不存在");
        } else {
            traversingMusicFile(localFile.getPath(), pathCallback);
        }

        ArrayList<MusicBean> musicList = new ArrayList<>();
        for (File file1 : filePaths) {
            MusicBean music = new MusicBean();
            music.name = ParseUtil.parseSongName(file1.getName());
            music.author = ParseUtil.parseAuthor(file1.getName());
            music.type = ParseUtil.parseType(file1.getName());
            music.path = file1.getPath();
            music.avatar = null;
            music.lyrics = null;
            musicList.add(music);
        }
        Log.d(TAG, "initData: musicList.size = " + musicList.size());
        musicRepository.insertMusic(musicList, localCallback);
    }

    /**
     * 遍历查找歌曲文件
     *
     * @param path 路径
     */
    private void traversingMusicFile (String path, LocalCallback<String> localCallback) {
        File file1 = new File(path);
        File[] files = file1.listFiles();
        for (File file : files) {
            boolean isContains = file.isFile() &&
                    (file.getName().contains(".mp3") && !file.getName().contains(".mp3.") || file.getName().contains(".flac"));
            if (isContains) {
                localCallback.onChangeData(file.getPath());
                filePaths.add(file);
            } else if (file.isDirectory()) {
                traversingMusicFile(file.getPath(), localCallback);
            }

        }
    }


}
