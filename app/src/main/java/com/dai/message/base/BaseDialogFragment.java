package com.dai.message.base;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dai.message.MainActivity;
import com.dai.message.R;

import java.lang.ref.WeakReference;

/**
 * 弹出dialog的基类
 * <p>
 * created by dht on 2018/7/23 10:25
 */
public class BaseDialogFragment extends DialogFragment {

    private static final String TAG = "BaseDialogFragment";

    protected static WeakReference<MainActivity> weakReference;

    /**
     * 在主界面中注册获取 MainActivity
     *
     * @param mainActivity MainActivity
     */
    public static void install(MainActivity mainActivity) {

        weakReference = new WeakReference<>(mainActivity);
    }

    /**
     * 绑定视图View
     */
    public void bindViews() {

    }

    /**
     * 显示菜单、选项、会议室数据列表
     */
    public void show() {
        FragmentTransaction ft = weakReference.get().getSupportFragmentManager().beginTransaction();
        show(ft, getTag());
    }

    /**
     * 设置菜单窗口属性参数
     */
    public void setMenuWindowParams() {
        Window window = getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorWhite)));
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.5f;
        lp.windowAnimations = R.style.MenuPopAnimation;
        window.setLayout(-1, -2);
        window.setAttributes(lp);
    }

    /**
     * 设置选项、会议室窗口属性参数
     */
    public void setWindowParams() {
        Window window = this.getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorWhite)));
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER;
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
    }

}
