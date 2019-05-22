package com.dai.message.util.rxjava;

import android.annotation.SuppressLint;
import android.util.Log;

import com.dai.message.callback.ConsumerCallBack;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.callback.ObserverCallback;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * RxJava Observable 封装工具
 * <p>
 * created by dht on 2018/7/6 09:05
 *
 * @author Administrator
 */

public class ObservableUtil {

    private static final String TAG = "ObservableUtil";

    private static ObservableUtil instance;

    public static final String KEY_SUCCESSFUL = "successful";


//    public static <T> void flatMap(){
//        Observable.create()
//    }

    /**
     * 异步执行方法
     *
     * @param observableCallback 被观察者回调接口
     * @param localCallback      回调接口
     */
    public static <T> void execute(ObservableCallback<T> observableCallback, LocalCallback<T> localCallback) {
        Observable.create(observableCallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber(localCallback));

    }

    /**
     * 返回并获取数据结果
     *
     * @param localCallback NetworkCallBack
     * @param <T>           T
     * @return Observer
     */
    public static <T> Observer<T> subscriber(final LocalCallback<T> localCallback) {
        return new ObserverCallback<T>() {
            @Override
            public void onNext(T value) {
                super.onNext(value);
                if (localCallback == null) return;
                localCallback.onChangeData(value);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(TAG, " onError: e = ", e);
                if (localCallback == null) {
                    return;
                }
                localCallback.onChangeData(null);
            }
        };
    }

    /**
     * 用于轮循红外测温数据
     *
     * @param localCallback 回调接口
     * @return Disposable
     */
    public Disposable getDisposable(final LocalCallback<Timed<Long>> localCallback) {
        return Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .timeInterval(TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(Timed<Long> longTimed) {
                        localCallback.onChangeData(longTimed);
                    }
                });
    }

    /**
     * 异步执行方法
     *
     * @param observableCallback        被观察者回调接口
     * @param consumerCallBack          消费者回调接口
     * @param throwableConsumerCallBack 消费者回调接口
     */
    @SuppressLint("CheckResult")
    public <T> void execute(ObservableCallback<T> observableCallback, ConsumerCallBack<T> consumerCallBack, ConsumerCallBack<Throwable> throwableConsumerCallBack) {
        Observable.create(observableCallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumerCallBack, throwableConsumerCallBack);
    }

}
