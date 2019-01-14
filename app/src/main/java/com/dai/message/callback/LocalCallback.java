package com.dai.message.callback;


/**
 * 基础回调接口
 * <p>
 * created by dht on 2018/7/5 18:35
 */
public interface LocalCallback<T> {

    void onChangeData(T data);
}
