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
import com.dai.message.util.LogUtil;
import com.dai.message.util.file.FileType;

import java.io.File;
import java.util.ArrayList;

public class LocalViewModel extends AndroidViewModel {

    private static final String TAG = "LocalViewModel";

    private ArrayList<File> filePaths = new ArrayList<>();

    private Application application;

    private MutableLiveData<ArrayList<File>> filesListData = new MutableLiveData<>();

    public LocalViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        traversalSong();
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
     * 播放音乐
     *
     * @param path 路径
     */
    public void playMusic(String path) {
        if (path == null) return;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //文件的类型
        String type = "";
        for (String[] array : FileType.MATCH_ARRAY) {
            //判断文件的格式
            if (path.contains(array[0])) {
                type = array[1];
                break;
            }
        }
        try {
            File file = new File(path);
            //判断是否是AndroidN以及更高的版本,设置intent的data和Type属性
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(application.getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(contentUri, type);
            } else {
                intent.setDataAndType(Uri.fromFile(file), type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            application.getApplicationContext().startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(application.getApplicationContext(), "无法打开该文件", Toast.LENGTH_SHORT).show();
            LogUtil.writeErrorInfo(TAG, "playMusic", "无法打开该文件");
            Log.e(TAG, "playMusic: e", e);
            e.printStackTrace();
        }
    }

    /**
     * 解析歌曲名称
     *
     * @param path 路径
     * @return 歌名
     */
    String parseSongName(String path) {
        if (path == null) return null;
        String[] strings = path.split("-");
        String[] songName = strings[strings.length-1].split("\\.");
        return songName[0];
    }

    /**
     * 解析歌手名
     *
     * @param path 路径
     * @return 歌手名
     */
    String parseUsername(String path) {
        if (path == null) return null;
        String[] username = path.split("-");
        return username[0];
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
}
