package com.dai.message.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dai.message.bean.IMusicAidlInterface;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.receiver.SendLocalBroadcast;
import com.dai.message.repository.MusicRepository;
import com.dai.message.repository.preferences.Config;
import com.dai.message.util.PlayType;
import com.dai.message.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * created by dht on 2019/1/10 16:15
 */

public class MusicService extends Service {


    //    private static final String TAG = "MusicService";
    private static final String TAG = "MusicTitleView";

    private MediaPlayer mediaPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    private MusicRepository repository;

    private List<Music> musicList = new ArrayList<>();

    private PlayType playType = PlayType.PLAY_IN_ORDER;

    private boolean isNext = true;

    private int currentPlayIndex = 0;

    private boolean isFirst = true;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new MusicRepository(getApplication());
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (isNext) {
                    ++currentPlayIndex;
                } else {
                    --currentPlayIndex;
                }
                mp.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
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
        return iBinder;
    }


    IMusicAidlInterface.Stub iBinder = new IMusicAidlInterface.Stub() {

        @Override
        public void playMusic(Music music) throws RemoteException {
            synchronized (Music.class) {
                try {
                    if (musicList.contains(music)) {
                        currentPlayIndex = musicList.lastIndexOf(music);
                    }
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(music.path);
                    mediaPlayer.prepareAsync();
                    Log.d("MusicTitleView", "playMusic: music = " + music);
                    Config.getInstance().setCurrentMusic(music);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("MusicTitleView", "playMusic: e", e);
                }
            }
        }

        @Override
        public void playCurrentMusic() throws RemoteException {
            Music music = Config.getInstance().getCurrentMusic();
            if (music == null) {
                ToastUtil.toastCustom(getApplicationContext(), "数据初始化中", 500);
                return;
            }
            playMusic(music);
        }

        @Override
        public void initPlayList() throws RemoteException {
            synchronized (Music.class) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
                    attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
                    mediaPlayer.setAudioAttributes(attrBuilder.build());
                }
                repository.getAllMusics(new LocalCallback<ArrayList<Music>>() {
                    @Override
                    public void onChangeData(ArrayList<Music> data) {
                        if (Config.getInstance().getCurrentMusic() == null && musicList.size() != 0) {
                            Config.getInstance().setCurrentMusic(data.get(0));
                        }
                        if (musicList.size() != data.size()) {
                            SendLocalBroadcast.getInstance().updateMusicView(getApplicationContext(), null, SendLocalBroadcast.KEY_UPDATE_VIEW);
                        }
                        musicList.clear();
                        musicList.addAll(data);

                    }
                });
            }
        }

        @Override
        public void setPlayType(int type) throws RemoteException {
            playType = PlayType.getPlayType(type);
            mediaPlayer.setLooping(type == PlayType.SINGLE_CYCLE.getIndex());
        }

        @Override
        public void playPause() throws RemoteException {
            synchronized (Music.class) {
                mediaPlayer.start();
            }
        }

        @Override
        public void pause() throws RemoteException {
            synchronized (Music.class) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        }

        @Override
        public void stop() throws RemoteException {
            synchronized (Music.class) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }

        @Override
        public void playPrevious() throws RemoteException {
            synchronized (Music.class) {
                isNext = false;
                switch (playType) {
                    case LIST_LOOP:
                    case PLAY_IN_ORDER:
                        if (currentPlayIndex >= musicList.size()) currentPlayIndex = 0;
                        if (currentPlayIndex <= -1) currentPlayIndex = musicList.size() - 1;
                        playMusic(musicList.get(currentPlayIndex));
                        break;
                    case SHUFFLE_PLAYBACK:
                        break;
                }
            }
        }

        @Override
        public void playNext() throws RemoteException {
            synchronized (Music.class) {
                isNext = true;
                switch (playType) {
                    case LIST_LOOP:
                    case PLAY_IN_ORDER:
                        if (currentPlayIndex >= musicList.size()) currentPlayIndex = 0;
                        if (currentPlayIndex <= -1) currentPlayIndex = musicList.size() - 1;
                        playMusic(musicList.get(currentPlayIndex));
                        break;
                    case SHUFFLE_PLAYBACK:
                        Random random = new Random();
                        int index = random.nextInt(musicList.size());
                        playMusic(musicList.get(index));
                        break;
                }
            }
        }

        @Override
        public void seekTo(int msec) throws RemoteException {
            synchronized (Music.class) {
                mediaPlayer.seekTo(msec);
            }
        }

        @Override
        public boolean isLooping() throws RemoteException {
            synchronized (Music.class) {
                return mediaPlayer.isLooping();
            }
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            synchronized (Music.class) {
                return mediaPlayer.isPlaying();

            }
        }

        @Override
        public int position() throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            synchronized (Music.class) {
                return mediaPlayer.getDuration();
            }
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public List<Music> getPlayList() throws RemoteException {
            synchronized (Music.class) {
                return musicList;
            }
        }

        @Override
        public Music getCurrentMusic() throws RemoteException {
            synchronized (Music.class) {
                return Config.getInstance().getCurrentMusic();
            }
        }

        @Override
        public void removeFromQueue(int position) throws RemoteException {

        }

        @Override
        public void clearQueue() throws RemoteException {

        }

        @Override
        public void showDesktopLyric(boolean show) throws RemoteException {

        }

        @Override
        public int AudioSessionId() throws RemoteException {
            return 0;
        }

        @Override
        public void release() throws RemoteException {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }


    };

}
