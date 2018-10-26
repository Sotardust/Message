package com.dai.message.util.rxjava;

import android.annotation.SuppressLint;


import com.dai.message.callback.CallBack;
import com.dai.message.callback.ConsumerCallBack;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.callback.ObserverCallback;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * RxJava Observable 封装工具
 * <p>
 * created by dht on 2018/7/6 09:05
 */

public class RxJavaObservable<T> {

    private static RxJavaObservable instance;

    public static final String KEY_SUCCESSFUL = "successful";

    private RxJavaObservable() {
    }

    public static RxJavaObservable getInstance() {
        if (instance == null)
            synchronized (RxJavaObservable.class) {
                if (instance == null)
                    instance = new RxJavaObservable();
            }
        return instance;
    }

    /**
     * 异步执行方法
     *
     * @param observableCallback 被观察者回调接口
     * @param observerCallBack   观察者回调接口
     */
    public void execute(ObservableCallback<T> observableCallback, ObserverCallback<T> observerCallBack) {
        Observable.create(observableCallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerCallBack);

    }

    /**
     * 用于轮循红外测温数据
     *
     * @param callBack 回调接口
     * @return Disposable
     */
    public Disposable getDisposable(final CallBack<Timed<Long>> callBack) {
        return Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .timeInterval(TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(Timed<Long> longTimed) {
                        callBack.onChangeData(longTimed);
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
    public void execute(ObservableCallback<T> observableCallback, ConsumerCallBack<T> consumerCallBack, ConsumerCallBack<Throwable> throwableConsumerCallBack) {
        Observable.create(observableCallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumerCallBack, throwableConsumerCallBack);
    }

}
