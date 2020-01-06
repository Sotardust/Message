package com.dht.message;

import android.app.Application;

import com.dht.baselib.base.BaseFragment;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.message.util.ScreenUtil;

/**
 * created by Administrator on 2018/10/24 16:58
 *
 * @author Administrator
 */
public class MessageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseFragment.install(this);
        ScreenUtil.install(getApplicationContext());
        MessagePreferences.INSTANCE.install(getApplicationContext());

    }
}
