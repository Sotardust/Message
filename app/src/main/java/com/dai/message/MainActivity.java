package com.dai.message;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.dai.message.base.BaseActivity;
import com.dai.message.bean.IMusicAidlInterface;
import com.dai.message.callback.LocalCallback;
import com.dai.message.repository.preferences.Config;
import com.dai.message.service.MusicService;
import com.dai.message.ui.dialog.MainDialogFragment;
import com.dai.message.ui.home.HomeFragment;
import com.dai.message.util.Key;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    private ServiceConnection connection;
    private IMusicAidlInterface musicService;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Config.getInstance().setIsFirstPlay(true);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: ");
                musicService = IMusicAidlInterface.Stub.asInterface(service);
                try {
                    musicService.initPlayList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (savedInstanceState == null) {
                    HomeFragment homeFragment = HomeFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putBinder(Key.IBINDER, (IBinder) musicService);
                    homeFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, homeFragment)
                            .commitNow();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: name" + name);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(this, MusicService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbindService(connection);
            if (musicService != null) {
                musicService.release();
            }
            Config.getInstance().setIsPlaying(false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // activity 都不可见的情况下，进程保活
        Log.d(TAG, "onTrimMemory: ");
    }

    @Override
    public void onBackPressed() {
        MainDialogFragment mainDialogFragment = MainDialogFragment.newInstance();
        mainDialogFragment.show(this);
        mainDialogFragment.setOkCallBack(new LocalCallback<String>() {
            @Override
            public void onSuccessful() {
                finish();
            }
        });
    }
}
