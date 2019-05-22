package com.dai.message.ui.music.recentplay;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.dai.message.R;
import com.dai.message.util.Key;
import com.dht.baselib.base.BaseActivity;

/**
 * created by dht on 2018/12/27 17:23
 */
public class RecentPlayActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            RecentPlayFragment recentPlayFragment = RecentPlayFragment.newInstance();
            Bundle bundle = new Bundle();
            IBinder iBinder = getIntent().getBundleExtra(Key.IBINDER).getBinder(Key.IBINDER);
            bundle.putBinder(Key.IBINDER, iBinder);
            recentPlayFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, recentPlayFragment)
                    .commitNow();
        }
    }

}
