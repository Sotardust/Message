package com.dai.message.ui.main;


import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.repository.entity.AllCallsEntity;

import java.util.ArrayList;

public class AllCallsViewModel extends CallRecordViewModel {

    private static final String TAG = "AllCallsViewModel";
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mAllCallsList;


    public AllCallsViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<ArrayList<AllCallsEntity>> getAllCallsList() {
        if (mAllCallsList == null) {
            mAllCallsList = new MutableLiveData<>();
            mAllCallsList.setValue(distinctAllCalls());
        }
        return mAllCallsList;
    }

    /**
     * 获取全部通话记录
     *
     * @return AllCalls集合类
     */
    private ArrayList<AllCallsEntity> distinctAllCalls() {
        ArrayList<AllCallsEntity> allCallsEntities = distinctAllCalls(0);
        for (AllCallsEntity entity : allCallsEntities) {
            repository.addAllCallsEntity(entity);
        }
        return allCallsEntities;
    }
}