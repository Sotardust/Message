// IMusicAidlInterface.aidl
package com.dht.databaselib.bean.music;

// Declare any non-default types here with import statements
import com.dht.databaselib.bean.music.MusicBean;

interface IMusicAidlInterface {

         void playMusic(in MusicBean music);

         void playCurrentMusic();

         void initPlayList();

         void setPlayType(int type);

         void playPause();

         void pause();

         void stop();

         void playPrevious();

         void playNext();

         void seekTo(int msec);

         boolean isLooping ();

         boolean isPlaying();

         int position();

         int getDuration();

         int getCurrentPosition();

         List<MusicBean> getPlayList();

         void setPlayList(in List<MusicBean> musics);

         MusicBean getCurrentMusic();

         void removeFromQueue(int position);

         void clearQueue();

         void showDesktopLyric(boolean show);

         int audioSessionId();

         void release () ;
}
