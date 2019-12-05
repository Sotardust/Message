package com.dht.message.ui.phone.refuse;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dht.message.repository.AllCallsRepository;
import com.dht.message.repository.entity.AllCallsEntity;
import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;

import java.util.ArrayList;
import java.util.List;

public class RefuseViewModel extends BaseAndroidViewModel {

    private AllCallsRepository repository;

    public RefuseViewModel(@NonNull Application application) {
        super(application);
        repository = new AllCallsRepository(application);
    }

    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mRefuseCallsList;


    public MutableLiveData<ArrayList<AllCallsEntity>> getRefuseCallsList() {
        if (mRefuseCallsList == null) {
            mRefuseCallsList = new MutableLiveData<>();
            distinctRefuseCalls();
        }
        return mRefuseCallsList;
    }

    /**
     * 获取未接来电通话记录
     * 拒接类型：5
     * <p>
     * AllCalls实体集合
     */
    private void distinctRefuseCalls() {
        repository.getCallsEntities(new LocalCallback<List<AllCallsEntity>>() {
            @Override
            public void onChangeData(List<AllCallsEntity> data) {
                mRefuseCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        }, "5");
    }
}
