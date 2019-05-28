package com.dht.baselib.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dht.baselib.R;


/**
 * 弹出dialog的基类
 * <p>
 * created by dht on 2018/7/23 10:25
 * @author Administrator
 */
public class BaseDialogFragment extends DialogFragment {

    private static final String TAG = "BaseDialogFragment";

    /**
     * 绑定视图View
     */
    public void bindViews() {

    }

    /**
     * 显示菜单、选项、会议室数据列表
     */
    public void show(BaseActivity activity) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        show(ft, getTag());
    }

    /**
     * 设置菜单窗口属性参数
     */
    public void setBottomWindowParams() {
        Window window = getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.windowAnimations = R.style.MenuPopAnimation;
        window.setAttributes(lp);
    }


    /**
     * 设置选项、会议室窗口属性参数
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setCenterWindowParams() {
        Window window = this.getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setLayout(-1, -2);
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.PopupWindowAnimation;
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
    }

}
