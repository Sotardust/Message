package com.dht.baselib.base;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    protected ImageView mTitleBarIcon;
    protected TextView mTitleBarMeetingRoom;
    protected ImageButton mTitleBarBack;
    protected TextView mTitleBarTitleName;
    protected Button mTitleBarAdvancedSet;
    protected Button mTitleBarVideoCtr;

    protected static WeakReference<Application> weakReference;

    /**
     * 在主界面中注册获取 Application
     *
     * @param application Application
     */
    public static void install (Application application) {

        weakReference = new WeakReference<>(application);
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void initViews (View view) {

    }

    @Override
    public void handlingClickEvents (View view) {
        //子类中重写改方法处理点击事件
    }

    @Override
    public void setTitleBarVisibilityOrText () {
        //子类中重写改方法设置TitleBar不同控件的显示效果
    }

    @Override
    public void onClick (View view) {
        handlingClickEvents(view);
    }


    /**
     * 绑定视图View
     */
    public void bindViews () {

    }

    /**
     * 初始化数据
     */
    public void initData () {

    }

    /**
     * 该方法处理视图View点击事件以及ViewModel观察监听数据
     */
    public void observerCallback () {

    }

}
