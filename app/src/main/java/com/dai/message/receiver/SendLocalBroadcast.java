package com.dai.message.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.dai.message.bean.Music;
import com.dai.message.util.Key;

/**
 * 发送本地广播工具类
 * <p>
 * created by dht on 2018/7/25 16:27
 */
public class SendLocalBroadcast {

    private static final String TAG = "SendLocalBroadcast";

    public static final String KEY_UPDATE_MUSIC_TITLE_VIEW = "updateMusicTitleView";
    public static final int KEY_UPDATE_VIEW = 0;
    public static final int KEY_UPDATE_DATA = 1;

    private SendLocalBroadcast() {
    }

    private static class BroadCast {
        private static SendLocalBroadcast broadcast = new SendLocalBroadcast();
    }

    public static SendLocalBroadcast getInstance() {
        return BroadCast.broadcast;
    }

    /**
     * 发送更新当前歌曲数据
     *
     * @param context Context
     * @param music   实体类
     * @param index   0\1 0:更新视图View 1：更新Music数据
     */
    public void updateMusicView(Context context, Music music, int index) {
        Intent intent = new Intent();
        if (index == KEY_UPDATE_DATA) {
            intent.putExtra(Key.CURRENT_MUSIC, music);
        }
        intent.putExtra(Key.INDEX, index);
        intent.setAction(KEY_UPDATE_MUSIC_TITLE_VIEW);
        LocalBroadcastManager.getInstance(context).sendBroadcastSync(intent);
    }
}
