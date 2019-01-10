// IMusicAidlInterface.aidl
package com.dai.message.bean;

// Declare any non-default types here with import statements
import com.dai.message.bean.Music;

interface IMusicAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void setDataSource(String path); //通过一个具体的路径来设置MediaPlayer的数据源，path可以是本地的一个路径，也可以是一个网络路径
//    void setDataSource(Context context, Uri uri) ; //通过给定的Uri来设置MediaPlayer的数据源，这里的Uri可以是网络路径或是一个ContentProvider的Uri。
//    void setDataSource(MediaDataSource dataSource); // 通过提供的MediaDataSource来设置数据源
//    void setDataSource(FileDescriptor fd); // 通过文件描述符FileDescriptor来设置数据源
//    int getCurrentPosition(); // 获取当前播放的位置
//    int getAudioSessionId(); // 返回音频的session ID
//    int getDuration(); // 得到文件的时间
//    TrackInfo[] getTrackInfo(); // 返回一个track信息的数组
//    boolean isLooping (); // 是否循环播放
//    boolean isPlaying(); // 是否正在播放
//    void pause () ; //暂停
//    void start () ; //开始
//    void stop () ; //停止
//    void prepare(); // 同步的方式装载流媒体文件。
//    void prepareAsync() ; //异步的方式装载流媒体文件。
//    void reset() ; //重置MediaPlayer至未初始化状态。
//    void release () ; //回收流媒体资源。
//    void seekTo(int msec) ;
//    void setAudioStreamType(int streamtype); // 指定流媒体类型
//    void setLooping(boolean looping) ; //设置是否单曲循环
//    void setNextMediaPlayer(MediaPlayer next); // 当 当前这个MediaPlayer播放完毕后，MediaPlayer next开始播放
//    void setWakeMode(Context context, int mode); //设置CPU唤醒的状态。

     void nextPlay(in Music music);

     void playMusic(in Music music);

     void playPlaylist(in List<Music> playlist,int id,String pid);

     void playPause();//播放已暂停的音乐

     void pause();

     void stop();

     void playPrevious();

     void playNext();

     void seekTo(int msec); //指定播放的位置（以毫秒为单位的时间）

     boolean isLooping (); // 是否循环播放

     boolean isPlaying(); // 是否正在播放

     int position();

     int getDuration();

     int getCurrentPosition();

     String getSongName();

     String getSongArtist();

     Music getPlayingMusic();

     List<Music> getPlayList();

     void removeFromQueue(int position);

     void clearQueue();

     void showDesktopLyric(boolean show);

     int AudioSessionId();

      void release () ; //回收流媒体资源。
}