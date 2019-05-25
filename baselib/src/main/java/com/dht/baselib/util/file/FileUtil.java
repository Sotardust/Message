package com.dht.baselib.util.file;

import android.util.Log;

/**
 * Created by dai on 2018/6/1.
 */

public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 日志文件名称
     */
    public static final String LOG_FILE = "log.txt";

    /**
     * 创建日志文件log.txt
     */
    public static void createLogFile() {
        Log.d(TAG, "createLogFile: ");
        String log = PathUtil.LOG_PATH + FileUtil.LOG_FILE ;
        FileManager.getInstance().createNewFile(log);
    }
}
