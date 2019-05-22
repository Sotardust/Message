package com.dht.baselib.callback;

public interface Callback<T> {

    void onChangeData(T data);

    void onChangeData();
}
