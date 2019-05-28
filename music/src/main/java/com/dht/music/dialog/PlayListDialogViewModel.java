package com.dht.music.dialog;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.repository.MusicRepository;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class PlayListDialogViewModel extends BaseAndroidViewModel {

    private MutableLiveData<ArrayList<MusicBean>> musicData = new MutableLiveData<>();

    private MusicRepository musicRepository;

    public PlayListDialogViewModel (@NonNull Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
    }

    public MutableLiveData<ArrayList<MusicBean>> getMusicData () {
        musicRepository.getAllMusics(new LocalCallback<ArrayList<MusicBean>>() {
            @Override
            public void onChangeData (ArrayList<MusicBean> data) {
                musicData.postValue(data);
            }
        });

        return musicData;
    }
}
