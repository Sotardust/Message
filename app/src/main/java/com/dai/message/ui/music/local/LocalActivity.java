package com.dai.message.ui.music.local;

import android.os.Bundle;

import com.dai.message.R;
import com.dai.message.base.BaseActivity;

/**
 * created by Administrator on 2018/12/27 17:23
 */
public class LocalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LocalFragment.newInstance())
                    .commitNow();
        }
    }

}
