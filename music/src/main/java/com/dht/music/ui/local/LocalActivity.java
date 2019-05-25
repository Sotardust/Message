package com.dht.music.ui.local;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.dht.baselib.base.BaseActivity;
import com.dht.music.R;

/**
 * created by Administrator on 2018/12/27 17:23
 *
 * @author Administrator
 */
public class LocalActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LocalFragment.newInstance())
                    .commitNow();
        }
    }


}
