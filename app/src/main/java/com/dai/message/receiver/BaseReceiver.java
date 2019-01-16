package com.dai.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dai.message.callback.LocalCallback;
import com.dai.message.util.Key;

/**
 * service ，fragment间通讯 及时更新视图view
 * <p>
 * created by dht on 2018/7/25 15:50
 */
public class BaseReceiver<T> extends BroadcastReceiver {

    private static final String TAG = "MusicTitleView";

    private LocalCallback<Integer> callBack;

    public BaseReceiver(LocalCallback<Integer> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        int index = intent.getIntExtra(Key.INDEX, -1);
        if (index == SendLocalBroadcast.KEY_UPDATE_VIEW) {
            int data = intent.getIntExtra(Key.INDEX, -1);
            callBack.onChangeData(data);
        }
        if (index == SendLocalBroadcast.KEY_UPDATE_DATA) {
//            callBack.onChangeData((T) intent.getParcelableExtra(Key.CURRENT_MUSIC));
        }
    }
}
