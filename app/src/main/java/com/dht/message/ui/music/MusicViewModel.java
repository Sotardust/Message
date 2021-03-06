package com.dht.message.ui.music;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dht.message.R;
import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.music.repository.MusicRepository;
import com.dht.music.repository.RecentPlayRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MusicViewModel extends BaseAndroidViewModel {


    private static final String TAG = "MusicViewModel";

    private MusicRepository repository;
    private RecentPlayRepository recentPlayRepository;
    private List<Integer> list = new ArrayList<>();

    private MutableLiveData<List<Integer>> musicData = new MutableLiveData<>();

    public MusicViewModel (@NonNull Application application) {
        super(application);
        repository = new MusicRepository(application);
        recentPlayRepository = new RecentPlayRepository(application);
        String[] strings = application.getResources().getStringArray(R.array.musicList);
        for (int i = 0; i < strings.length; i++) {
            list.add(0);
        }
    }


    /**
     * 获取music页item对应个数
     *
     * @return MutableLiveData
     */
    public MutableLiveData<List<Integer>> getEndListData () {

        repository.getMusicTotal(new LocalCallback<Integer>() {
            @Override
            public void onChangeData (Integer data) {
                list.set(0, data);
                musicData.postValue(list);
            }
        });

        recentPlayRepository.getPlayTotal(new LocalCallback<Integer>() {
            @Override
            public void onChangeData (Integer data) {
                super.onChangeData(data);
                list.set(1, data);
                musicData.postValue(list);
            }
        });

        return musicData;
    }


}
