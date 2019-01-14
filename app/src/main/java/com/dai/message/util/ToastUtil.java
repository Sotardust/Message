package com.dai.message.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {


    public static void toastShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    public static void toastCustom(Context context, @StringRes int resId, int duration) {
        final Toast toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
        toast.show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }

    public static void toastCustom(Context context, String msg, int duration) {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }
}
