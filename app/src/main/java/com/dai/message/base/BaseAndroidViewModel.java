package com.dai.message.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * 含有Application上下文的ViewModel
 * <p>
 * created by dht on 2018/6/29 14:48
 */
public class BaseAndroidViewModel extends AndroidViewModel {

    protected Application application;

    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }
}
