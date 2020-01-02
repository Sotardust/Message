package com.dht.baselib.music;

import com.dht.databaselib.bean.music.MusicBean;

import java.util.List;

/**
 * @author Administrator
 */
public class MusicContact {

    interface Presenter {
        void playMusic (MusicBean music);

        void playCurrentMusic ();

        /**
         * 初始化播放列表
         */
        void initPlayList ();

        void setPlayType (int type);

        /**
         * 播放已暂停的音乐
         */
        void playPause ();//

        void pause ();

        void stop ();

        void playPrevious ();

        void playNext ();

        /**
         * 指定播放的位置（以毫秒为单位的时间）
         *
         * @param msec int
         */
        void seekTo (int msec);

        /**
         * 是否循环播放
         *
         * @return 是否循环播放
         */
        boolean isLooping ();

        /**
         * 是否正在播放
         *
         * @return Boolean
         */
        boolean isPlaying ();

        int position ();

        int getDuration ();

        int getCurrentPosition ();

        List<MusicBean> getPlayList ();

        MusicBean getCurrentMusic ();

        void removeFromQueue (int position);

        void clearQueue ();

        void showDesktopLyric (boolean show);

        int audioSessionId ();

        /**
         * 回收流媒体资源
         */
        void release ();
    }
}
