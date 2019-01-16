package com.dai.message;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.repository.MusicRepository;
import com.dai.message.util.LogUtil;

import java.io.File;
import java.util.ArrayList;

public class WelcomeModel extends BaseAndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private MusicRepository musicRepository;
    private ArrayList<File> filePaths = new ArrayList<>();

    public WelcomeModel(@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
    }

    /**
     * 初始化数据
     */
    public void initDatabaseData(LocalCallback<String> pathCallback, LocalCallback<String> localCallback) {
        String neteasePath = Environment.getExternalStorageDirectory() + File.separator + "netease";
        String localPath = Environment.getExternalStorageDirectory() + File.separator + "Music";
        File neteaseFile = new File(neteasePath);
        File localFile = new File(localPath);
        if (!neteaseFile.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", neteasePath + "路径不存在");
            Log.d(TAG, "searchSong: " + neteasePath + "路径不存在");
            return;
        }
        if (!localFile.exists()) {
            LogUtil.writeInfo(TAG, "traversalSong", localFile + "路径不存在");
            Log.d(TAG, "searchSong: " + localFile + "路径不存在");
            return;
        }
        if (filePaths.size() != 0) filePaths.clear();
        traversingMusicFile(neteaseFile.getPath(), pathCallback);
        traversingMusicFile(localFile.getPath(), pathCallback);

        ArrayList<Music> musicList = new ArrayList<>();
        for (File file1 : filePaths) {
            Music music = new Music();
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
    private void traversingMusicFile(String path, LocalCallback<String> localCallback) {
        File file1 = new File(path);
        File[] files = file1.listFiles();
        for (File file : files) {
            if (file.isFile() && (file.getName().contains(".mp3") && !file.getName().contains(".mp3.") || file.getName().contains(".flac"))) {
                localCallback.onChangeData(file.getPath());
                filePaths.add(file);
            } else if (file.isDirectory())
                traversingMusicFile(file.getPath(), localCallback);

        }
    }

    /**
     * 解析歌曲名称
     *
     * @param name 文件名
     * @return 歌名
     */
    private String parseSongName(String name) {
        if (name == null) return null;
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
    private String parseAuthor(String name) {
        if (name == null) return null;
        String[] username = name.split("-");
        return username[0];
    }

    /**
     * 解析歌曲类型
     *
     * @param name 文件名
     * @return 歌曲类型
     */
    private String parseType(String name) {
        if (name == null) return null;
        String[] type = name.split("\\.");
        return type[1];
    }
}
