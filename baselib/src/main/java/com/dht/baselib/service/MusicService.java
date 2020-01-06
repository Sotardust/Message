package com.dht.baselib.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dht.baselib.util.PlayType;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.IMusicAidlInterface;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.eventbus.RxBus;
import com.dht.eventbus.event.InitPlayListEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * created by dht on 2019/1/10 16:15
 *
 * @author Administrator
 */

public class MusicService extends Service {


    private static final String TAG = "MusicService";


    private MediaPlayer mediaPlayer;
    /**
     * 启动app初始化一次后不再进行 初始化
     */
    private boolean isFirstInit = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private List<MusicBean> musicList = new ArrayList<>();

    private boolean isNext = true;

    private int currentPlayIndex = 0;

    private boolean isFirst = true;

    private MusicBean currentMusic;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared() called with: mp = [" + mp + "]");
                if (isNext) {
                    ++currentPlayIndex;
                } else {
                    --currentPlayIndex;
                }
                mp.start();
                Log.d(TAG, "onPrepared: isPlaying = " + mp.isPlaying());
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion() called with: mp = [" + mp + "]");
                try {
                    if (isFirst) {
                        isFirst = false;
                        return;
                    }
                    iBinder.playNext();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onError() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
                if (!mp.isPlaying()) {
                    mp.start();
                }
                return false;
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return iBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        try {
            isFirstInit = true;
            iBinder.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    IMusicAidlInterface.Stub iBinder = new IMusicAidlInterface.Stub() {

        @Override
        public void playMusic(MusicBean music) {
            synchronized (MusicBean.class) {
                try {
                    if (musicList.contains(music)) {
                        currentPlayIndex = musicList.lastIndexOf(music);
                    }
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(music.path);
                    mediaPlayer.prepare();
                    Log.d("MusicTitleView", "playMusic: music = " + music);
                    currentMusic = music;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("MusicTitleView", "playMusic: e", e);
                }
            }
        }


        @Override
        public void playCurrentMusic() {
            if (currentMusic == null) {
                ToastUtil.toastCustom(getApplicationContext(), "数据初始化中", 500);
                return;
            }
            playMusic(currentMusic);
        }

        @Override
        public void initPlayList() {
            Log.d(TAG, "initPlayList: ");
            if (!isFirstInit) {
                return;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
                attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
                mediaPlayer.setAudioAttributes(attrBuilder.build());
            }
            RxBus.getInstance().post(new InitPlayListEvent());
            isFirstInit = false;
        }

        @Override
        public void setPlayType(int type) {
            MessagePreferences.INSTANCE.setPlayType(type);
            mediaPlayer.setLooping(type == PlayType.SINGLE_CYCLE.getIndex());
        }

        @Override
        public void playPause() {
            synchronized (MusicBean.class) {
                if (currentMusic == null) {
                    playMusic(MessagePreferences.INSTANCE.getCurrentMusic());
                } else {
                    mediaPlayer.start();
                }
            }
        }

        @Override
        public void pause() {
            synchronized (MusicBean.class) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        }

        @Override
        public void stop() {
            synchronized (MusicBean.class) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }

        @Override
        public void playPrevious() {
            synchronized (MusicBean.class) {
                isNext = false;
                final PlayType playType = PlayType.values()[MessagePreferences.INSTANCE.getPlayType()];
                switch (playType) {
                    case LIST_LOOP:
                    case PLAY_IN_ORDER:
                        if (currentPlayIndex >= musicList.size()) {
                            currentPlayIndex = 0;
                        }
                        if (currentPlayIndex <= -1) {
                            currentPlayIndex = musicList.size() - 1;
                        }
                        playMusic(musicList.get(currentPlayIndex));
                        break;
                    case SHUFFLE_PLAYBACK:
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void playNext() {
            synchronized (MusicBean.class) {
                isNext = true;
                final PlayType playType = PlayType.values()[MessagePreferences.INSTANCE.getPlayType()];
                switch (playType) {
                    case LIST_LOOP:
                    case PLAY_IN_ORDER:
                        if (currentPlayIndex >= musicList.size()) {
                            currentPlayIndex = 0;
                        }
                        if (currentPlayIndex <= -1) {
                            currentPlayIndex = musicList.size() - 1;
                        }
                        playMusic(musicList.get(currentPlayIndex));
                        break;
                    case SHUFFLE_PLAYBACK:
                        Random random = new Random();
                        int index = random.nextInt(musicList.size());
                        playMusic(musicList.get(index));
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void seekTo(int msec) {
            mediaPlayer.seekTo(msec);
        }

        @Override
        public boolean isLooping() {
            return mediaPlayer.isLooping();
        }

        @Override
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        @Override
        public int position() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public List<MusicBean> getPlayList() {
            return musicList;
        }

        @Override
        public void setPlayList(List<MusicBean> musics) {
            musicList = musics;
        }

        @Override
        public MusicBean getCurrentMusic() {

            if (currentMusic == null)
                return MessagePreferences.INSTANCE.getCurrentMusic();

            return currentMusic;
        }

        @Override
        public void removeFromQueue(int position) {

        }

        @Override
        public void clearQueue() {

        }

        @Override
        public void showDesktopLyric(boolean show) {

        }

        @Override
        public int audioSessionId() {
            return 0;
        }

        @Override
        public void release() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    };

}
