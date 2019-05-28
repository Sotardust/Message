package com.dht.baselib.util;

import android.util.Log;


import com.dht.baselib.util.file.FileUtil;
import com.dht.baselib.util.file.PathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件日志存储工具
 * Created by dai on 2018/6/1.
 */
public class LogUtil {

    private static final String TAG = "LogUtil";

    private static final String logPath = PathUtil.LOG_PATH + FileUtil.LOG_FILE;

    private LogUtil() {

    }


    /**
     * 把日志信息写入到文件
     * @param tag 方法名
     * @param info 信息
     */
    private static void write(String tag, String info) {
        FileOutputStream fos = null;
        try {
            File file = new File(logPath);
            fos = new FileOutputStream(file, true);
            fos.write(getFormatInfo(tag, info).getBytes());
            fos.flush();
        } catch (IOException e) {
            Log.e(TAG, "writeInfo: e", e);
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e(TAG, "writeInfo: fos e", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 输入正常信息
     * @param tag 方法名
     * @param msg 信息
     * @param info 信息
     */
    public static void writeInfo(String tag, String msg, String info) {
        write(tag, msg + info);

    }

    /**
     * 输入异常信息
     * @param tag 方法名
     * @param msg 信息
     * @param info 信息
     */
    public static void writeErrorInfo(String tag, String msg, String info) {
        write(tag, msg + "error： " + info);
    }

    /**
     * 格式化数据
     * @param tag
     * @param info
     * @return 格式化的字符串
     */
    private static String getFormatInfo(String tag, String info) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(new Date()) + " : " + tag + " : " + info + "\n";

    }

    /**
     * 清除log日志
     */
    private static void deleteLogInfo() {
        //TODO 当log.txt 文件达到一定5M时 清除时间存留较长的文件
    }


}
