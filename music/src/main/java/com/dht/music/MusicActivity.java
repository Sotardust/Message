package com.dht.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.dht.baselib.base.BaseActivity;
import com.dht.databaselib.bean.music.IMusicAidlInterface;
import com.dht.music.service.MusicService;

/**
 * created by dht on 2019/5/23 0023 22:20
 *
 * @author dht
 */
public class MusicActivity extends BaseActivity {

    private static final String TAG = "MusicActivity";

    private ServiceConnection connection;
    private static IMusicAidlInterface iBinder;
    protected static MusicModel mModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected (ComponentName name, IBinder service) {
                iBinder = IMusicAidlInterface.Stub.asInterface(service);
                if (mModel == null) {
                    mModel = new MusicModel(iBinder);
                    mModel.initPlayList();
                }
            }
            @Override
            public void onServiceDisconnected (ComponentName name) {
                Toast.makeText(getApplicationContext(), "音乐服务启动失败！", Toast.LENGTH_SHORT).show();
            }
        };

    }

    public static MusicModel getModel () {
        return mModel;
    }

    @Override
    protected void onResume () {
        super.onResume();
        bindService(new Intent(this, MusicService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onTrimMemory (int level) {
        super.onTrimMemory(level);
        // activity 都不可见的情况下，进程保活
        Log.d(TAG, "onTrimMemory: ");
    }


}
