package com.dai.message.ui.main.dial;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.callback.CallBack;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dai.message.ui.main.CallRecordViewModel;

import java.util.ArrayList;
import java.util.List;

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
            distinctDialCalls();
        }
        return mDialCallsList;
    }

    /**
     * 获取拨打通话记录
     * 已接类型：2
     *
     * @return AllCalls实体集合
     */
    private void distinctDialCalls() {
        repository.getCallsEntities(new CallBack<List<AllCallsEntity>>() {
            @Override
            public void onChangeData(List<AllCallsEntity> data) {
                mDialCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        }, "2");
    }
}
