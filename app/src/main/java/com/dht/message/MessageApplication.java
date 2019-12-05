package com.dht.message;

import android.app.Application;

import com.dht.message.util.ScreenUtil;
import com.dht.databaselib.preferences.MessagePreferences;

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
        MessagePreferences.install(getApplicationContext());

    }
}
