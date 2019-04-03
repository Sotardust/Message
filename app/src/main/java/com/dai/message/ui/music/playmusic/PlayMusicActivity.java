package com.dai.message.ui.music.playmusic;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.dai.message.R;
import com.dai.message.util.Key;
import com.dht.commonlib.base.BaseActivity;

/**
 * created by dht on 2019/01/07 17:50:24
 */
public class PlayMusicActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putBinder(Key.IBINDER, getIntent().getBundleExtra(Key.IBINDER).getBinder(Key.IBINDER));
            bundle.putParcelable(Key.MUSIC, getIntent().getParcelableExtra(Key.MUSIC));
            playMusicFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, playMusicFragment)
                    .commitNow();
        }

    }

}