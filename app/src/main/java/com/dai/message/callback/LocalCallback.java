package com.dai.message.callback;


import com.dht.commonlib.callback.Callback;

/**
 * 基础回调接口
 * <p>
 * created by dht on 2018/7/5 18:35
 */
public class LocalCallback<T> implements Callback<T> {


    @Override
    public void onChangeData(T data) {

    }

    @Override
    public void onChangeData() {

    }
}
