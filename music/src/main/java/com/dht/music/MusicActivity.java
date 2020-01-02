package com.dht.music;

import android.os.Bundle;
import android.util.Log;

import com.dht.baselib.base.BaseActivity;

/**
 * created by dht on 2019/5/23 0023 22:20
 *
 * @author dht
 */
public class MusicActivity extends BaseActivity {

    private static final String TAG = "MusicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // activity 都不可见的情况下，进程保活
        Log.d(TAG, "onTrimMemory: ");
    }


}
