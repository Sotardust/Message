package com.dai.message.ui.music.download;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.Music;
import com.dai.message.repository.RecentPlayRepository;

import java.util.List;

public class DownloadedViewModel extends BaseAndroidViewModel {


    private MutableLiveData<List<Music>> musicData;

    private RecentPlayRepository recentPlayRepository;

    public DownloadedViewModel(@NonNull Application application) {
        super(application);
        recentPlayRepository = new RecentPlayRepository(application);
    }

    public MutableLiveData<List<Music>> getDownloadedEntityData() {

        if (musicData == null) {
            musicData = new MutableLiveData<>();

        }
        return musicData;
    }



}
