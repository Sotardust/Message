package com.dai.message.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.dai.message.ui.phone.SecondaryLinkageViewModel;
import com.dai.message.ui.phone.allcalls.AllCallsViewModel;

/**
 * 创建工厂传参
 * <p>
 * created by dht on 2018/8/10 11:23
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);

                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SecondaryLinkageViewModel.class)) {
            //noinspection unchecked
            return (T) new SecondaryLinkageViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(AllCallsViewModel.class)) {
            //noinspection unchecked
            return (T) new AllCallsViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
