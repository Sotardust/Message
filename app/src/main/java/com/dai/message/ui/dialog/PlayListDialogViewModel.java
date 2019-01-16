package com.dai.message.ui.dialog;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.repository.MusicRepository;

import java.util.ArrayList;

public class PlayListDialogViewModel extends BaseAndroidViewModel {

    private MutableLiveData<ArrayList<Music>> musicData = new MutableLiveData<>();


    private MusicRepository musicRepository;

    public PlayListDialogViewModel(@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
    }

    public MutableLiveData<ArrayList<Music>> getMusicData() {
        musicRepository.getAllMusics(new LocalCallback<ArrayList<Music>>() {
            @Override
            public void onChangeData(ArrayList<Music> data) {
                musicData.postValue(data);
            }
        });

        return musicData;
    }
}
