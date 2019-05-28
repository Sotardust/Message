package com.dai.message.ui.login;

import android.os.Bundle;

import com.dai.message.R;
import com.dht.music.MusicActivity;

/**
 * @author Administrator
 */
public class LoginActivity extends MusicActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }

}
