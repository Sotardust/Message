package com.dai.message.bean;

public class BaseModel<T> {

    /**
     * -2 : 服务器未设置返回结果（默认返回值）
     * -1 : 服务器程序异常
     * 0 : 返回成功
     * 1 : 返回失败
     */
    public int code = -2;

    public String msg = null;

    public T result = null;
}
