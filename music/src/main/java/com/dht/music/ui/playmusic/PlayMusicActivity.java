package com.dht.music.ui.playmusic;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.dht.music.MusicActivity;
import com.dht.music.R;

/**
 * created by dht on 2019/01/07 17:50:24
 *
 * @author Administrator
 */
public class PlayMusicActivity extends MusicActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, playMusicFragment)
                    .commitNow();
        }

    }

}