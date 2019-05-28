package com.dai.message.ui.phone.answered;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dai.message.repository.AllCallsRepository;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class AnsweredViewModel extends BaseAndroidViewModel {
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mAnsweredCallsList;
    private AllCallsRepository repository;

    public AnsweredViewModel (@NonNull Application application) {
        super(application);
        repository = new AllCallsRepository(application);
    }

    public MutableLiveData<ArrayList<AllCallsEntity>> getAnsweredCallsList () {
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
    private void distinctAnsweredCalls () {
        repository.getCallsEntities(new LocalCallback<List<AllCallsEntity>>() {
            @Override
            public void onChangeData (List<AllCallsEntity> data) {
                mAnsweredCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        }, "1");
    }
}
