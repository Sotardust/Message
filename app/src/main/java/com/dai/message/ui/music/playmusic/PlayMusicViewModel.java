package com.dai.message.ui.music.playmusic;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.dai.message.BuildConfig;
import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.util.LogUtil;
import com.dai.message.util.file.FileType;

import java.io.File;

public class PlayMusicViewModel extends BaseAndroidViewModel {

    private static final String TAG = "PlayMusicViewModel";

    public PlayMusicViewModel(@NonNull Application application) {
        super(application);
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

}
