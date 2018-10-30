package com.dai.message.ui.main.answered;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.callback.CallBack;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dai.message.ui.main.CallRecordViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnsweredViewModel extends CallRecordViewModel {
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mAnsweredCallsList;

    public AnsweredViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<AllCallsEntity>> getAnsweredCallsList() {
        if (mAnsweredCallsList == null) {
            mAnsweredCallsList = new MutableLiveData<>();
            distinctAnsweredCalls();
        }
        return mAnsweredCallsList;
    }

    /**
     * 获取已接来电通话记录
     * 已接类型：1
     *
     * @return AllCalls实体集合
     */
    private void distinctAnsweredCalls() {
        repository.getCallsEntities(new CallBack<List<AllCallsEntity>>() {
            @Override
            public void onChangeData(List<AllCallsEntity> data) {
                mAnsweredCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        }, "1");
    }
}
