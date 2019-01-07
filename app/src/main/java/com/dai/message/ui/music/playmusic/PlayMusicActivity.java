package com.dai.message.ui.music.playmusic;

import android.os.Bundle;

import com.dai.message.R;
import com.dai.message.base.BaseActivity;
import com.dai.message.ui.home.HomeFragment;

/**
 * created by dht on 2019/01/07 17:50:24
 */
public class PlayMusicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlayMusicFragment.newInstance())
                    .commitNow();
        }

    }


}