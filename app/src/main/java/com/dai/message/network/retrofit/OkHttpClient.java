package com.dai.message.network.retrofit;

/**
 * 单例创建 OKHttpClient 实例
 * <p>
 * created by dht on 2018/12/24 10:18
 */
public class OkHttpClient {

    private static OkHttpClient client;

    private static okhttp3.OkHttpClient.Builder builder;

    private OkHttpClient() {
        builder = new okhttp3.OkHttpClient.Builder();
    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    public okhttp3.OkHttpClient.Builder getBuilder() {
        return builder;
    }
}
