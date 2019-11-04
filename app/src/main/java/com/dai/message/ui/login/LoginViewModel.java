package com.dai.message.ui.login;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dht.baselib.callback.NetworkCallback;
import com.dht.baselib.util.ToastUtil;
import com.dht.baselib.util.file.FileManager;
import com.dht.baselib.util.file.PathUtil;
import com.dht.network.BaseModel;

import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {


    private static final String TAG = "LoginViewModel";

    private Application application;

    private LoginApi loginApi;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        loginApi = new LoginApi();

    }


    /**
     * 登录
     *
     * @param name          用户
     * @param password      密码
     * @param loginCallBack 登录回调接口
     */
    public void logon(String name, String password, NetworkCallback<BaseModel<String>> loginCallBack) {

        loginApi.logon(name, password, loginCallBack);
    }

    /**
     * 注册账号
     *
     * @param name         用户名
     * @param password     密码
     * @param registerTime 注册时间
     */
    public void register(String name, String password, String registerTime) {
        loginApi.register(name, password, registerTime, registerCallBack);
    }

    private NetworkCallback<BaseModel<String>> registerCallBack = new NetworkCallback<BaseModel<String>>() {
        @Override
        public void onChangeData(BaseModel<String> model) {
            if (model == null) {
                ToastUtil.toastCustom(application.getApplicationContext(), "网络超时", 200);
                return;
            }
            ToastUtil.toastCustom(application.getApplicationContext(), model.msg, 200);
        }
    };


    public void initData() {
        generateDimensFile("dimens-320x480px.xml", 2, 1 / 2);
        generateDimensFile("dimens-480x800px.xml", 2, 0.88);
        generateDimensFile("dimens-1280x720px.xml", 2, 1);
        generateDimensFile("dimens-1920x10800px.xml", 2, 1.5);
    }

    /**
     * 产生dimens文件（包含dp（1~500）与sp（1~100））
     *
     * @param fileName 文件名
     * @param standard 以（1280x720为基准 1dp =2px
     * @param multiple 转换倍数
     */
    private void generateDimensFile(final String fileName, final double standard, final double multiple) {

        io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws IOException {

                String path = PathUtil.MUSIC_PATH + fileName;
                StringBuilder builder = new StringBuilder();
                builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
                builder.append("<resources>").append("\n");

                for (int i = 0; i < 1000; i++) {
                    builder.append("<dimen name=\"").append("px_")
                            .append(i).append("\">").append(String.format("%.1f", (i / standard * multiple)))
                            .append("dp</dimen>\n");
                }

                for (int i = 0; i < 200; i++) {
                    builder.append("<dimen name=\"").append("sp_")
                            .append(i).append("\">").append(String.format("%.1f", (i / standard * multiple)))
                            .append("sp</dimen>\n");
                }

                builder.append("</resources>").append("\n");

                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(FileManager.getInstance().createNewFile(path));
                    fileOutputStream.write(builder.toString().getBytes());
                    fileOutputStream.flush();//将内容一次性写入文件
                    emitter.onNext("写入成功" + path);
                } catch (IOException e) {
                    Log.d(TAG, "run() returned: " + e);
                    e.printStackTrace();
                    emitter.onNext("写入失败");
                } finally {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: s = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
