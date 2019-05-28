package com.dai.message.ui.phone.dial;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.repository.AllCallsRepository;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;

import java.util.ArrayList;
import java.util.List;

public class DialViewModel extends BaseAndroidViewModel {

    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mDialCallsList;
    private AllCallsRepository repository;

    public DialViewModel(@NonNull Application application) {
        super(application);
        repository = new AllCallsRepository(application);
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
        repository.getCallsEntities(new LocalCallback<List<AllCallsEntity>>() {
            @Override
            public void onChangeData(List<AllCallsEntity> data) {
                mDialCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        }, "2");
    }
}
