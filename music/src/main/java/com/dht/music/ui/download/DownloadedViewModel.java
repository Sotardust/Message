package com.dht.music.ui.download;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;


import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.repository.RecentPlayRepository;

import java.util.List;

public class DownloadedViewModel extends BaseAndroidViewModel {


    private MutableLiveData<List<MusicBean>> musicData;

    private RecentPlayRepository recentPlayRepository;

    public DownloadedViewModel(@NonNull Application application) {
        super(application);
        recentPlayRepository = new RecentPlayRepository(application);
    }

    public MutableLiveData<List<MusicBean>> getDownloadedEntityData() {

        if (musicData == null) {
            musicData = new MutableLiveData<>();

        }
        return musicData;
    }



}
