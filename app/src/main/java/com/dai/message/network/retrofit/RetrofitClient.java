package com.dai.message.network.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by dht on 2018/12/24 10:22
 */
public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private static RetrofitClient instance;

    private Retrofit.Builder mRetrofitBuilder;

    private static final int DEFAULT_TIMEOUT = 60;

    private RetrofitClient() {
        okhttp3.OkHttpClient okHttpClient = OkHttpClient.getInstance().getBuilder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getHttpLoggingInterceptor())
                .addNetworkInterceptor(getRequestHeader())
                .build();

        mRetrofitBuilder = new Retrofit.Builder()
                //添加CallAdapterFactory 用Observable<ResponseBody>接收 添加自定义CallAdapterFactory 则用用Observable<T>接收
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 添加ConverterFactory 用Call<ResponseBody>接收 添加自定义ConverterFactory 则用Call<T>接收
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);
    }

    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    private Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    private Retrofit getRetrofit(String baseUrl) {
        return getRetrofitBuilder()
                .baseUrl(baseUrl)
                .build();
    }

    private Retrofit getRetrofit() {
        return getRetrofitBuilder()
                .build();
    }

    public <T> T create(String baseUrl, final Class<T> service) {
        return getRetrofit(baseUrl).create(service);
    }

    public <T> T create(final Class<T> service) {
        return getRetrofit().create(service);
    }

    /**
     * 日志拦截器
     *
     * @return HttpLoggingInterceptor
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("OkHttp", "log message = " + message);

            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 请求头拦截器
     *
     * @return Interceptor
     */
    private static Interceptor getRequestHeader() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                okhttp3.Request originalRequest = chain.request();
                okhttp3.Request.Builder builder = originalRequest.newBuilder();

                //OkHttp 不支持上传中文字符，使用编码URLEncoder.encode(file.getName())
                //使用 URLDecoder.decode(file.getName(), "UTF-8")解码
                builder.addHeader("Content-Type", "application/json; charset=utf-8");
//                builder.addHeader("token", "d08986536b5e3678119aac9b892439a8");
                okhttp3.Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
                okhttp3.Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

}
