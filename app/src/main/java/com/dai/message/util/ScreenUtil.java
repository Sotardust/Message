package com.dai.message.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * created by Administrator on 2018/12/13 18:28
 */
public class ScreenUtil {


    public static void install(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        WIDTH = point.x;
        HEIGHT = point.y;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int WIDTH;
    public static int HEIGHT;

}
