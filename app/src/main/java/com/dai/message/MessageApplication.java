package com.dai.message;

import android.app.Application;

import com.dai.message.base.BaseFragment;
import com.dai.message.util.ScreenUtil;

/**
 * created by Administrator on 2018/10/24 16:58
 */
public class MessageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseFragment.install(this);
        ScreenUtil.install(getApplicationContext());

    }
}
