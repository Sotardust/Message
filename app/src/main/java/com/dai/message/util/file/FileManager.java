package com.dai.message.util.file;

import android.util.Log;


import com.dai.message.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件操作管理工具
 * Created by Administrator on 2018/6/26.
 */

public class FileManager  {

    private static final String TAG = "FileManager";
    private static  FileManager instance;

    private FileManager(){
        initDirectory();
    }

    public static FileManager getInstance(){
        if (instance ==null){
            synchronized (FileManager.class){
                if (instance ==null){
                    instance = new FileManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化文件目录
     */
    private void initDirectory(){
        createDirectory();
    }

    /**
     * 创建文件目录
     */
    private void createDirectory() {
        ArrayList<String> directoryList = new ArrayList<>();
        directoryList.add(PathUtil.MUSIC_PATH);
        directoryList.add(PathUtil.LOG_PATH);
        for (String path : directoryList) {
            File directory = new File(path);
            if (!directory.exists()) {
                boolean isSuccessful = directory.mkdirs();
                Log.d(TAG, "createDirectory: isSuccessful" + isSuccessful);
            }
        }
    }

    /**
     * 创建新文件
     * @param path 文件路径
     * @return 文件对象
     */
    public File createNewFile(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                boolean isSuccessful = file.createNewFile();
                Log.d(TAG, "createNewFile: " + path + (isSuccessful ? "成功" : "失败"));
            }
        } catch (IOException e) {
            LogUtil.writeErrorInfo(TAG, "createNewFile", e.toString());
            e.printStackTrace();
        }

        return file;
    }
}
