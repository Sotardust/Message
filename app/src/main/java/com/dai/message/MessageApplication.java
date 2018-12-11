package com.dai.message;

import android.app.Application;

import com.dai.message.base.BaseFragment;

/**
 * created by Administrator on 2018/10/24 16:58
 */
public class MessageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseFragment.install(this);

    }
}
