package com.dai.message.ui.music.recentplay;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.callback.LocalCallback;
import com.dai.message.repository.RecentPlayRepository;
import com.dai.message.repository.entity.RecentPlayEntity;

import java.util.ArrayList;
import java.util.List;

public class RecentPlayViewModel extends BaseAndroidViewModel {


    private static final String TAG = "RecentPlayViewModel";

    private MutableLiveData<List<RecentPlayEntity>> musicData = new MutableLiveData<>();

    private RecentPlayRepository recentPlayRepository;

    public RecentPlayViewModel(@NonNull Application application) {
        super(application);
        recentPlayRepository = new RecentPlayRepository(application);
    }

    /**
     * 获取最近播放实体类
     *
     * @return musicData
     */
    public MutableLiveData<List<RecentPlayEntity>> getRecentPlayEntityData() {
        recentPlayRepository.getRecentPlayEntities(new LocalCallback<List<RecentPlayEntity>>() {
            @Override
            public void onChangeData(List<RecentPlayEntity> data) {
                musicData.postValue(data);
            }
        });
        return musicData;
    }

    /**
     * 按最近播放时间升序或降序排列
     *
     * @param entities RecentPlayEntity集合
     * @return 排序后的RecentPlayEntity集合
     */
    public List<RecentPlayEntity> getRecentPlayAscending(List<RecentPlayEntity> entities) {

        List<RecentPlayEntity> list = new ArrayList<>();



        return entities;
    }

}
