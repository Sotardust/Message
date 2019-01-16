package com.dai.message.callback;

public interface Callback<T> {
    void onChangeData(T data);

    void onSuccessful();
}
