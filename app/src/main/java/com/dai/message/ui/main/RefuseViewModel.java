package com.dai.message.ui.main;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.repository.entity.AllCallsEntity;

import java.util.ArrayList;

public class RefuseViewModel extends CallRecordViewModel {
    public RefuseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mRefuseCallsList;


    public MutableLiveData<ArrayList<AllCallsEntity>> getRefuseCallsList() {
        if (mRefuseCallsList == null) {
            mRefuseCallsList = new MutableLiveData<>();
            mRefuseCallsList.setValue(distinctRefuseCalls());
        }
        return mRefuseCallsList;
    }
    /**
     * 获取未接来电通话记录
     * 拒接类型：5
     *
     * @return AllCalls实体集合
     */
    private ArrayList<AllCallsEntity> distinctRefuseCalls() {
        return distinctAllCalls(5);
    }
}
