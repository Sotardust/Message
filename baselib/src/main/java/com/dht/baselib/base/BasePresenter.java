package com.dht.baselib.base;

/**
 * created by Administrator on 2019/1/7 17:51
 *
 * @author Administrator
 */
public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}
