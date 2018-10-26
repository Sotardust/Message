package com.dai.message.ui.main;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.repository.entity.AllCallsEntity;

import java.util.ArrayList;


public class MissedCallsViewModel extends CallRecordViewModel {

    public MissedCallsViewModel(@NonNull Application application) {
        super(application);
    }
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mMissedCallsList;


    public MutableLiveData<ArrayList<AllCallsEntity>> getMissedCallsList() {
        if (mMissedCallsList == null) {
            mMissedCallsList = new MutableLiveData<>();
            mMissedCallsList.setValue(distinctMissedCalls());
        }
        return mMissedCallsList;
    }
    /**
     * 获取未接来电通话记录
     * 未接类型：3
     * @return AllCalls实体集合
     */
    private ArrayList<AllCallsEntity> distinctMissedCalls() {
       return  distinctAllCalls(3);
    }
}
