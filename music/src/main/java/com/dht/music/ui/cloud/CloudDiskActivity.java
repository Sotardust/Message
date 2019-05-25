package com.dht.music.ui.cloud;

import android.os.Bundle;

import com.dht.baselib.base.BaseActivity;
import com.dht.music.R;

/**
 * created by Administrator on 2018/12/27 17:23
 *
 * @author Administrator
 */
public class CloudDiskActivity extends BaseActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CloudDiskFragment.newInstance())
                    .commitNow();
        }
    }
}
