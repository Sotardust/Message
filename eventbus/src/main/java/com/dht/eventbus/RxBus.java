package com.dht.eventbus;

import com.dht.eventbus.event.Event;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author Administrator
 */
public class RxBus {

    private final Subject<Object> mBus;

    private RxBus () {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance () {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public void post (Event event) {
        mBus.onNext(event);
    }

    public <T> Observable<T> toObservable (Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers () {
        return mBus.hasObservers();
    }

}
