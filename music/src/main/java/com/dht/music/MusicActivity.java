package com.dht.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.dht.baselib.base.BaseActivity;
import com.dht.databaselib.bean.music.IMusicAidlInterface;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.music.service.MusicService;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dht on 2019/5/23 0023 22:20
 *
 * @author dht
 */
public class MusicActivity extends BaseActivity implements MusicContact.MusicPresenter {

    private static final String TAG = "MusicActivity";

    private ServiceConnection connection;
    private static IMusicAidlInterface iBinder;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected (ComponentName name, IBinder service) {
                iBinder = IMusicAidlInterface.Stub.asInterface(service);
                initPlayList();
            }

            @Override
            public void onServiceDisconnected (ComponentName name) {
                Toast.makeText(getApplicationContext(), "音乐服务启动失败！", Toast.LENGTH_SHORT).show();
            }
        };

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

    /**
     * 播放音乐
     *
     * @param music MusicBean
     */
    @Override
    public void playMusic (MusicBean music) {
        try {
            iBinder.playMusic(music);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放当前音乐
     */
    @Override
    public void playCurrentMusic () {
        try {
            iBinder.playCurrentMusic();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化播放列表
     */
    @Override
    public void initPlayList () {
        try {
            iBinder.initPlayList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置播放类型
     *
     * @param type 类型
     */
    @Override
    public void setPlayType (int type) {
        try {
            iBinder.setPlayType(type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放已暂停的音乐
     */
    @Override
    public void playPause () {
        try {
            iBinder.playPause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    @Override
    public void pause () {
        try {
            iBinder.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放音乐
     */
    @Override
    public void stop () {
        try {
            iBinder.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放上一首音乐
     */
    @Override
    public void playPrevious () {
        try {
            iBinder.playPrevious();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放下一首音乐
     */
    @Override
    public void playNext () {
        try {
            iBinder.playNext();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定播放的位置（以毫秒为单位的时间）
     *
     * @param msec 指定位置
     */
    @Override
    public void seekTo (int msec) {
        try {
            iBinder.seekTo(msec);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否循环播放
     *
     * @return 是否循环播放
     */
    @Override
    public boolean isLooping () {
        try {
            return iBinder.isLooping();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否正在播放
     *
     * @return 布尔型
     */
    @Override
    public boolean isPlaying () {
        try {
            iBinder.isPlaying();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int position () {
        try {
            return iBinder.position();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取音乐时长
     *
     * @return 时长
     */
    @Override
    public int getDuration () {
        try {
            return iBinder.getDuration();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前音乐播放位置
     *
     * @return 位置
     */
    @Override
    public int getCurrentPosition () {
        try {
            iBinder.getCurrentPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取播放列表
     *
     * @return 音乐列表集合
     */
    @Override
    public List<MusicBean> getPlayList () {
        try {
            return iBinder.getPlayList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取当前音乐
     *
     * @return 音乐实体类
     */
    @Override
    public MusicBean getCurrentMusic () {
        try {
            return iBinder.getCurrentMusic();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return getPlayList().get(0);
    }

    /**
     * 移除 队列
     *
     * @param position 队列下表
     */
    @Override
    public void removeFromQueue (int position) {
        try {
            iBinder.removeFromQueue(position);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清楚队列
     */
    @Override
    public void clearQueue () {
        try {
            iBinder.clearQueue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置是否显示桌面歌词
     *
     * @param show 是否显示
     */
    @Override
    public void showDesktopLyric (boolean show) {
        try {
            iBinder.showDesktopLyric(show);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int audioSessionId () {
        try {
            iBinder.audioSessionId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 回收流媒体资源
     */
    @Override
    public void release () {
        try {
            iBinder.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
