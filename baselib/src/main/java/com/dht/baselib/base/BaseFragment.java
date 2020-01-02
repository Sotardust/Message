package com.dht.baselib.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dht.baselib.music.MusicModel;
import com.dht.baselib.service.MusicService;
import com.dht.databaselib.bean.music.IMusicAidlInterface;

import java.lang.ref.WeakReference;


/**
 * 管理Fragment视图
 * <p>
 * created by dht on 2018/6/29 14:47
 *
 * @author Administrator
 */
public class BaseFragment extends Fragment implements ITitleBarManager {

    private static final String TAG = "BaseFragment";

    protected static WeakReference<Application> weakReference;
    private ServiceConnection connection;
    private IMusicAidlInterface iBinder;
    protected MusicModel mModel;

    /**
     * 在主界面中注册获取 Application
     *
     * @param application Application
     */
    public static void install(Application application) {

        weakReference = new WeakReference<>(application);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iBinder = IMusicAidlInterface.Stub.asInterface(service);
                if (mModel == null) {
                    mModel = new MusicModel(iBinder);
                    mModel.initPlayList();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(getContext(), "音乐服务启动失败！", Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() != null) {
            getContext().bindService(new Intent(getActivity(), MusicService.class), connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getContext() != null) {
            getContext().unbindService(connection);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void initViews(View view) {

    }

    @Override
    public void handlingClickEvents(View view) {
        //子类中重写改方法处理点击事件
    }

    @Override
    public void setTitleBarVisibilityOrText() {
        //子类中重写改方法设置TitleBar不同控件的显示效果
    }

    @Override
    public void onClick(View view) {
        handlingClickEvents(view);
    }


    /**
     * 绑定视图View
     */
    public void bindViews() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 该方法处理视图View点击事件以及ViewModel观察监听数据
     */
    public void observerCallback() {

    }

}
