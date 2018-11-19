package com.dai.message.repository;

import android.app.Application;
import android.util.Log;

import com.dai.message.callback.CallBack;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.callback.ObserverCallback;
import com.dai.message.repository.dao.AllCallsDao;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dai.message.util.LogUtil;
import com.dai.message.util.rxjava.RxJavaObservable;

import java.util.List;

import io.reactivex.ObservableEmitter;

/**
 * 对 AllCallsEntity 数据以及数据库进行操作
 * <p>
 * created by Administrator on 2018/10/26 11:19
 */
public class AllCallsRepository {
    private static final String TAG = "AllCallsRepository";


    private AllCallsDao allCallsDao;

    public AllCallsRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        allCallsDao = appDatabase.allCallsDao();
    }

    /**
     * 向数据库中添加AllCallsEntity 实体类
     *
     * @param entity AllCallsEntity
     */
    @SuppressWarnings("unchecked")
    public void addAllCallsEntity(final AllCallsEntity entity) {
        LogUtil.writeInfo(TAG, "addAllCallsEntity", entity.toString());
        RxJavaObservable.getInstance().execute(new ObservableCallback<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                allCallsDao.addAllCallsEntity(entity);
                emitter.onNext("successful");
            }
        }, new ObserverCallback<String>() {
            @Override
            public void onNext(String entities) {
                super.onNext(entities);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(TAG, "onError: ", e);
            }
        });
    }

    /**
     * 获取数据库中的AllCallsEntity 实体类集合数据
     *
     * @param callBack 回调接口
     */
    @SuppressWarnings("unchecked")
    public void getAllCallsEntities(final CallBack<List<AllCallsEntity>> callBack) {
        getCallsEntities(callBack, "0");
    }

    /**
     * 获取数据库中的AllCallsEntity 实体类集合数据
     *
     * @param callBack 回调接口
     * @param callType 1/2/3/4/5 接听/拨打/未接//拒接
     */
    @SuppressWarnings("unchecked")
    public void getCallsEntities(final CallBack<List<AllCallsEntity>> callBack, final String callType) {
        LogUtil.writeInfo(TAG, "getCallsEntities", callType);
        RxJavaObservable.getInstance().execute(new ObservableCallback<List<AllCallsEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AllCallsEntity>> emitter) throws Exception {
                super.subscribe(emitter);
                List<AllCallsEntity> entities = "0".equals(callType) ?
                        allCallsDao.findAllCallsEntities() :
                        allCallsDao.findCallsEntities(callType);
                emitter.onNext(entities);

            }
        }, new ObserverCallback<List<AllCallsEntity>>() {
            @Override
            public void onNext(List<AllCallsEntity> entities) {
                super.onNext(entities);
                callBack.onChangeData(entities);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(TAG, "onError: ", e);
            }
        });
    }
}
