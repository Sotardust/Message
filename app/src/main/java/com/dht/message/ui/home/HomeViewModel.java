package com.dht.message.ui.home;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.util.LogUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.repository.MusicRepository;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Administrator
 */
public class HomeViewModel extends BaseAndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private MusicRepository musicRepository;
    private ArrayList<File> filePaths = new ArrayList<>();

    public HomeViewModel (@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
    }

    /**
     * 初始化数据
     */
    public void initDatabaseData (LocalCallback<String> localCallback) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Music";
        File file = new File(path);
        if (!file.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", path + "路径不存在");
            Log.d(TAG, "searchSong: " + path + "路径不存在");
            return;
        }
        if (filePaths.size() != 0) {
            filePaths.clear();
        }
        traversingMusicFile(file.getPath());
        ArrayList<MusicBean> musicList = new ArrayList<>();
        for (File file1 : filePaths) {
            MusicBean music = new MusicBean();
            music.name = parseSongName(file1.getName());
            music.author = parseAuthor(file1.getName());
            music.type = parseType(file1.getName());
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
    private void traversingMusicFile (String path) {
        File file1 = new File(path);
        File[] files = file1.listFiles();
        Log.d(TAG, "searchSongFile: files.length = " + files.length);
        for (File file : files) {
            boolean isContains = file.isFile() &&
                    (file.getName().contains(".mp3") && !file.getName().contains(".mp3.") || file.getName().contains(".flac"));
            if (isContains) {
                Log.d(TAG, "findSong: " + file.getPath());
                filePaths.add(file);
            } else if (file.isDirectory()) {
                traversingMusicFile(file.getPath());
            }
        }
    }

    /**
     * 解析歌曲名称
     *
     * @param name 文件名
     * @return 歌名
     */
    private String parseSongName (String name) {
        if (name == null) {
            return null;
        }
        String[] strings = name.split("-");
        String[] songName = strings[strings.length - 1].split("\\.");
        return songName[0];
    }

    /**
     * 解析歌手名
     *
     * @param name 文件名
     * @return 歌手名
     */
    private String parseAuthor (String name) {
        if (name == null) {
            return null;
        }
        String[] username = name.split("-");
        return username[0];
    }

    /**
     * 解析歌曲类型
     *
     * @param name 文件名
     * @return 歌曲类型
     */
    private String parseType (String name) {
        if (name == null) {
            return null;
        }
        String[] type = name.split("\\.");
        return type[1];
    }
}
