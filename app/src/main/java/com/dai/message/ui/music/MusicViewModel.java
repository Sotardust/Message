package com.dai.message.ui.music;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.callback.LocalCallback;
import com.dai.message.repository.MusicRepository;

import java.io.File;
import java.util.ArrayList;

public class MusicViewModel extends BaseAndroidViewModel {


    private static final String TAG = "MusicViewModel";

    private MusicRepository repository;

    private MutableLiveData<Integer> musicData = new MutableLiveData<>();

    public MusicViewModel(@NonNull Application application) {
        super(application);
        repository = new MusicRepository(application);
    }


    public MutableLiveData<Integer> getMusicData() {

        repository.getMusicTotal(new LocalCallback<Integer>() {
            @Override
            public void onChangeData(Integer data) {
                musicData.postValue(data);
            }
        });

        return musicData;
    }




}
