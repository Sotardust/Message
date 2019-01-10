package com.dai.message;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dai.message.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }
        MediaPlayer player = new MediaPlayer();

    }



    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // activity 都不可见的情况下，进程保活
        Log.d(TAG, "onTrimMemory: ");
    }
}
