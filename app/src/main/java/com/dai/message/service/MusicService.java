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
import com.dai.message.repository.MusicRepository;
import com.dai.message.util.PlayType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * created by dht on 2019/1/10 16:15
 */

public class MusicService extends Service {


    private static final String TAG = "MusicService";

    private MediaPlayer mediaPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    private MusicRepository repository;

    private List<Music> musicList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new MusicRepository(getApplication());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
//        return null;
        return iBinder;
    }

    private static PlayType playType = PlayType.PLAY_IN_ORDER;
    IMusicAidlInterface.Stub iBinder = new IMusicAidlInterface.Stub() {
        @Override
        public void setDataSource(String path) {
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                if (mediaPlayer.isPlaying()) return;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
                    attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
                    mediaPlayer.setAudioAttributes(attrBuilder.build());
                }
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "setDataSource: e", e);
            }
        }


        @Override
        public void nextPlay(Music music) {

        }


        @Override
        public void playMusic(Music music) throws RemoteException {
            setDataSource(music.path);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }

        @Override
        public void initPlayList() throws RemoteException {
            repository.getAllMusics(new LocalCallback<ArrayList<Music>>() {
                @Override
                public void onChangeData(ArrayList<Music> data) {
                    musicList.addAll(data);
                }
            });
        }

        @Override
        public void setPlayType(int type) throws RemoteException {
            playType = PlayType.getPlayType(type);
            mediaPlayer.setLooping(type == PlayType.SINGLE_CYCLE.getIndex());
        }

        @Override
        public void playPause() throws RemoteException {
            mediaPlayer.start();
        }

        @Override
        public void pause() throws RemoteException {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }

        @Override
        public void playPrevious() throws RemoteException {
            switch (playType) {
                case LIST_LOOP:
                    break;
                case PLAY_IN_ORDER:
                    break;
                case SHUFFLE_PLAYBACK:
                    break;
            }
        }

        @Override
        public void playNext() throws RemoteException {
            switch (playType) {
                case LIST_LOOP:
                    break;
                case PLAY_IN_ORDER:
                    break;
                case SHUFFLE_PLAYBACK:
                    break;
            }
        }

        @Override
        public void seekTo(int msec) throws RemoteException {
            mediaPlayer.seekTo(msec);
        }

        @Override
        public boolean isLooping() throws RemoteException {
            return mediaPlayer.isLooping();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return mediaPlayer.isPlaying();
        }

        @Override
        public int position() throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            return mediaPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public List<Music> getPlayList() throws RemoteException {
            return musicList;
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
