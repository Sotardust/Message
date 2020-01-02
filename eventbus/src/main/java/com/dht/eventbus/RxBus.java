package com.dht.eventbus;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author Administrator
 */
public class RxBus {

    private final Subject<Object> mBus;
    private HashMap<String, Disposable> hashMap;


    private RxBus() {
        mBus = PublishSubject.create().toSerialized();

        hashMap = new HashMap<>();
    }

    public static RxBus getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public void post(Object event) {
        mBus.onNext(event);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    /**
     * 若订阅过一次则不再订阅 避免重复订阅
     *
     * @param eventType     事件类型
     * @param rxCallBack 回调接口
     * @param <T>           T
     */
    public <T> void toRxBusResult(final Class<T> eventType, final RxCallBack<T> rxCallBack) {
        Disposable disposable = hashMap.get(eventType.getName());
        if (disposable == null) {
            Observable<T> observable = mBus.ofType(eventType);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<T>() {
                        @Override
                        public void onNext(T value) {
                            rxCallBack.onCallBack(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            hashMap.remove(eventType.getName());
                        }

                        @Override
                        public void onComplete() {

                        }
                        @Override
                        public void onSubscribe(Disposable d) {
                            hashMap.put(eventType.getName(), d);
                        }
                    });
            return;
        }
        disposable.dispose(); //取消订阅后再次调用该方法
        hashMap.remove(eventType.getName());
        toRxBusResult(eventType, rxCallBack);
    }

}
