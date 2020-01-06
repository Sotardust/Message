package com.dht.network;

/**
 * 服务器返回状态码
 * <p>
 * created by dht on 2020/1/6 17:20
 */
public class HttpStatusCode {
    /**
     * 103 : sessionId 会话超时
     * 102 : 服务器未设置返回结果（默认返回值）
     * 101 : 服务器程序异常
     * 100 : 返回成功
     * 99 : 返回失败
     * 98 : 默认值
     */
    public static final int CODE_99 = 99;
    public static final int CODE_100 = 100;
    public static final int CODE_101 = 101;
    public static final int CODE_102 = 102;
    public static final int CODE_103 = 103;
}
