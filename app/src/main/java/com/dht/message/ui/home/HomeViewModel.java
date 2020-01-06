package com.dht.message.ui.home;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.util.LogUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.repository.MusicRepository;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Administrator
 */
public class HomeViewModel extends BaseAndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private MusicRepository musicRepository;
    private ArrayList<File> filePaths = new ArrayList<>();

    public HomeViewModel (@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
    }

}
