package com.dai.message.network.retrofit;

import android.util.Log;


import com.dai.message.callback.NetworkCallback;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.callback.ObserverCallback;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * created by dht on 2018/12/24 10:53
 */
public class BaseApi {

    /**
     * 异步获取数据函数
     *
     * @param observableCallback ObservableCallback
     * @param observerCallback   ObserverCallback
     */
    @SuppressWarnings("unchecked")
    public void ansyObtainData(ObservableCallback observableCallback, ObserverCallback observerCallback) {
        Observable.create(observableCallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerCallback);
    }

    /**
     * 抽出异步部操作处理方法
     *
     * @param call            Call
     * @param networkCallback 回调接口
     * @param field           方法名
     * @param TAG             TAG
     */
    protected <T> void ansyOperationHandle(final Call<T> call, final NetworkCallback<T> networkCallback, final String field, final String TAG) {

        ansyObtainData(new ObservableCallback<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                super.subscribe(emitter);
                Response<T> response = call.execute();
                emitter.onNext(response.body());
            }
        }, new ObserverCallback<T>() {
            @Override
            public void onNext(T value) {
                super.onNext(value);
                if (networkCallback == null) return;
                networkCallback.onChangeData(value);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (networkCallback == null) {
                    Log.e(TAG, field + " onError: e = ", e);
                    return;
                }
                networkCallback.onChangeData(null);
            }
        });
    }
}
