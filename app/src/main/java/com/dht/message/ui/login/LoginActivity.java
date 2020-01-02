package com.dht.message.ui.login;

import android.os.Bundle;

import com.dht.message.R;
import com.dht.music.MusicActivity;

/**
 * @author Administrator
 */
public class LoginActivity extends MusicActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }

}
