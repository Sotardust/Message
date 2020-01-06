package com.dht.baselib.music;

import android.os.RemoteException;

import com.dht.databaselib.bean.music.IMusicAidlInterface;
import com.dht.databaselib.bean.music.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MusicModel implements MusicContact.Presenter {


    private IMusicAidlInterface iBinder;

    public MusicModel(IMusicAidlInterface iBinder) {
        this.iBinder = iBinder;
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
     * 更改播放列表
     */
    @Override
    public void setPlayList(List<MusicBean> musics) {
        try {
            iBinder.setPlayList(musics);
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
            return iBinder.isPlaying();
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
