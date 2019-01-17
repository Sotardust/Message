package com.dai.message.ui.music.recentplay;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.repository.RecentPlayRepository;

import java.util.List;

public class RecentPlayViewModel extends BaseAndroidViewModel {


    private static final String TAG = "RecentPlayViewModel";

    private MutableLiveData<List<Music>> musicData = new MutableLiveData<>();

    private RecentPlayRepository recentPlayRepository;

    public RecentPlayViewModel(@NonNull Application application) {
        super(application);
        recentPlayRepository = new RecentPlayRepository(application);
    }

    public MutableLiveData<List<Music>> getMusicData() {
        recentPlayRepository.getRecentPlayEntities(new LocalCallback<List<Music>>() {
            @Override
            public void onChangeData(List<Music> data) {
                musicData.postValue(data);
            }
        });
        return musicData;
    }

}
