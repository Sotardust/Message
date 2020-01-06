package com.dht.message;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.dht.message.ui.dialog.ExitDialog;
import com.dht.message.ui.home.HomeFragment;
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
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed () {
        ExitDialog exitDialog = ExitDialog.newInstance();
        exitDialog.show(this);
        exitDialog.setOkCallBack(new LocalCallback<String>() {
            @Override
            public void onChangeData () {
                finish();
            }
        });
    }
}
