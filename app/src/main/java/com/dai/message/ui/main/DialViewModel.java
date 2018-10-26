package com.dai.message.ui.main;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.repository.entity.AllCallsEntity;

import java.util.ArrayList;

public class DialViewModel extends CallRecordViewModel {

    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mDialCallsList;


    public DialViewModel(@NonNull Application application) {
        super(application);
    }




    public MutableLiveData<ArrayList<AllCallsEntity>> getDialCallsList() {
        if (mDialCallsList == null) {
            mDialCallsList = new MutableLiveData<>();
            mDialCallsList.setValue(distinctDialCalls());
        }
        return mDialCallsList;
    }
    /**
     * 获取拨打通话记录
     * 已接类型：2
     *
     * @return AllCalls实体集合
     */
    private ArrayList<AllCallsEntity> distinctDialCalls() {
        return distinctAllCalls(2);
    }
}
