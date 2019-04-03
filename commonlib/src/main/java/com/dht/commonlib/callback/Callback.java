package com.dht.commonlib.callback;

public interface Callback<T> {

    void onChangeData(T data);

    void onChangeData();
}
