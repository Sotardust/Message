package com.dai.message;

import android.app.Application;

import com.dai.message.repository.preferences.Config;
import com.dai.message.util.ScreenUtil;

/**
 * created by Administrator on 2018/10/24 16:58
 *
 * @author Administrator
 */
public class MessageApplication extends Application {

    @Override
    public void onCreate () {
        super.onCreate();
        ScreenUtil.install(getApplicationContext());
        Config.install(getApplicationContext());

    }
}
