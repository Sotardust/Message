package com.dai.message.ui.other;


import android.app.Application;
import android.arch.lifecycle.ViewModel;

public class SecondaryLinkageViewModel extends ViewModel {

    private Application mApplication;

    public SecondaryLinkageViewModel(Application mApplication) {
        this.mApplication = mApplication;
    }
}
