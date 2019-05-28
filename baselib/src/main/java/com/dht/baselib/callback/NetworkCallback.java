package com.dht.baselib.callback;

/**
 * created by dht on 2018/12/24 13:50
 */
public interface NetworkCallback<T> {

    void onChangeData(T data);
}
