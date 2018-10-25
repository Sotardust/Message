package com.dai.message.util.file;

import android.os.Environment;


import com.dai.message.BuildConfig;

import java.io.File;

/**
 * 放置各个文件路径名
 * <p>
 * Created by Administrator on 2018/6/26.
 */

public class PathUtil {

    /**
     * 以包名作为文件总路径
     */
    private static final String PATH = Environment.getExternalStorageDirectory()
            + File.separator + BuildConfig.APPLICATION_ID + File.separator;

    /**
     * 数据文件路径
     */
    public static final String MUSIC_PATH = PATH + "data" + File.separator;

    /**
     * 日志路径
     */
    public static final String LOG_PATH = PATH + "log" + File.separator;

}
