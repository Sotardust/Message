package com.dai.message;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.dai.message.ui.dialog.MainDialogFragment;
import com.dai.message.ui.home.HomeFragment;
import com.dht.baselib.callback.LocalCallback;
import com.dht.music.MusicActivity;

/**
 * @author Administrator
 */
public class MainActivity extends MusicActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate (final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed () {
        MainDialogFragment mainDialogFragment = MainDialogFragment.newInstance();
        mainDialogFragment.show(this);
        mainDialogFragment.setOkCallBack(new LocalCallback<String>() {
            @Override
            public void onChangeData () {
                finish();
            }
        });
    }
}
