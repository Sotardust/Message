package com.dht.message.ui.phone.allcalls;


import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dht.message.repository.entity.AllCallsEntity;
import com.dht.message.ui.phone.CallRecordViewModel;
import com.dht.baselib.callback.LocalCallback;

import java.util.ArrayList;
import java.util.List;

public class AllCallsViewModel extends CallRecordViewModel {

    private static final String TAG = "AllCallsViewModel";
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mAllCallsList;


    public AllCallsViewModel (@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<ArrayList<AllCallsEntity>> getAllCallsList () {
        if (mAllCallsList == null) {
            mAllCallsList = new MutableLiveData<>();
            distinctAllCalls();
        }
        return mAllCallsList;
    }

    /**
     * 获取全部通话记录
     *
     * @return AllCalls集合类
     */
    private void distinctAllCalls () {
        distinctAllCalls(new LocalCallback<List<AllCallsEntity>>() {
            @Override
            public void onChangeData (List<AllCallsEntity> data) {
                mAllCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        });
    }
}