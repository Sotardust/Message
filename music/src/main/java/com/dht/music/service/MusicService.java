package com.dht.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.IMusicAidlInterface;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.music.repository.MusicRepository;
import com.dht.music.repository.RecentPlayRepository;
import com.dht.music.util.PlayType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dht.music.util.PlayType.LIST_LOOP;
import static com.dht.music.util.PlayType.PLAY_IN_ORDER;
import static com.dht.music.util.PlayType.SHUFFLE_PLAYBACK;


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
    public int onStartCommand (Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private MusicRepository repository;
    private RecentPlayRepository recentPlayRepository;

    private List<MusicBean> musicList = new ArrayList<>();

    private boolean isNext = true;

    private int currentPlayIndex = 0;

    private boolean isFirst = true;

    @Override
    public void onCreate () {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        repository = new MusicRepository(getApplication());
        recentPlayRepository = new RecentPlayRepository(getApplication());
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        MessagePreferences.getInstance().setIsPlaying(false);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared (MediaPlayer mp) {
                if (isNext) {
                    ++currentPlayIndex;
                } else {
                    --currentPlayIndex;
                }
                mp.start();
                Log.d(TAG, "onPrepared: isPlaying = " + mp.isPlaying());
                MessagePreferences.getInstance().setIsPlaying(mp.isPlaying());
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion (MediaPlayer mp) {
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
            public boolean onError (MediaPlayer mp, int what, int extra) {
                if (!mp.isPlaying()) {
                    mp.start();
                }
                return false;
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        Log.d(TAG, "onBind: ");
        return iBinder;
    }

    @Override
    public void onDestroy () {
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
        public void playMusic (MusicBean music) throws RemoteException {
            synchronized (MusicBean.class) {
                try {
                    if (musicList.contains(music)) {
                        currentPlayIndex = musicList.lastIndexOf(music);
                    }
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(music.path);
                    mediaPlayer.prepareAsync();
                    Log.d("MusicTitleView", "playMusic: music = " + music);
                    MessagePreferences.getInstance().setCurrentMusic(music);
                    recentPlayRepository.insertOrUpdate(music);
//                    SendLocalBroadcast.getInstance().updateMusicView(getApplicationContext(), null, SendLocalBroadcast.KEY_UPDATE_VIEW);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("MusicTitleView", "playMusic: e", e);
                }
            }
        }


        @Override
        public void playCurrentMusic () throws RemoteException {
            MusicBean music = MessagePreferences.getInstance().getCurrentMusic();
            if (music == null) {
                ToastUtil.toastCustom(getApplicationContext(), "数据初始化中", 500);
                return;
            }
            playMusic(music);
        }

        @Override
        public void initPlayList () throws RemoteException {
            synchronized (MusicBean.class) {
                if (!isFirstInit) {
                    return;
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
                    attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
                    mediaPlayer.setAudioAttributes(attrBuilder.build());
                }
                repository.getAllMusics(new LocalCallback<ArrayList<MusicBean>>() {
                    @Override
                    public void onChangeData (ArrayList<MusicBean> data) {
                        if (MessagePreferences.getInstance().getCurrentMusic() == null) {
                            MessagePreferences.getInstance().setCurrentMusic(data.get(0));
                        }
//                        Log.d(TAG, "onChangeData: musicList.size() = " + musicList.size() + "data.size() = " + data.size());
//                        if (musicList.size() != data.size()) {
//                            SendLocalBroadcast.getInstance().updateMusicView(getApplicationContext(), null, SendLocalBroadcast.KEY_UPDATE_VIEW);
//                        }
                        musicList.clear();
                        musicList.addAll(data);

                    }
                });
                isFirstInit = false;
            }
        }

        @Override
        public void setPlayType (int type) throws RemoteException {

            MessagePreferences.getInstance().setPlayType(type);
            mediaPlayer.setLooping(type == PlayType.SINGLE_CYCLE.getIndex());
        }

        @Override
        public void playPause () throws RemoteException {
            synchronized (MusicBean.class) {
                mediaPlayer.start();
                MessagePreferences.getInstance().setIsPlaying(true);
            }
        }

        @Override
        public void pause () throws RemoteException {
            synchronized (MusicBean.class) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                MessagePreferences.getInstance().setIsPlaying(false);
            }
        }

        @Override
        public void stop () throws RemoteException {
            synchronized (MusicBean.class) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }

        @Override
        public void playPrevious () throws RemoteException {
            synchronized (MusicBean.class) {
                isNext = false;
//                switch (MessagePreferences.getInstance().getPlayType()) {
//                    case LIST_LOOP:
//                    case PLAY_IN_ORDER:
//                        if (currentPlayIndex >= musicList.size()) {
//                            currentPlayIndex = 0;
//                        }
//                        if (currentPlayIndex <= -1) {
//                            currentPlayIndex = musicList.size() - 1;
//                        }
//                        playMusic(musicList.get(currentPlayIndex));
//                        break;
//                    case SHUFFLE_PLAYBACK:
//                        break;
//                    default:
//                        break;
//                }
            }
        }

        @Override
        public void playNext () throws RemoteException {
            synchronized (MusicBean.class) {
                isNext = true;
//                switch (MessagePreferences.getInstance().getPlayType()) {
//                    case LIST_LOOP:
//                    case PLAY_IN_ORDER:
//                        if (currentPlayIndex >= musicList.size()) {
//                            currentPlayIndex = 0;
//                        }
//                        if (currentPlayIndex <= -1) {
//                            currentPlayIndex = musicList.size() - 1;
//                        }
//                        playMusic(musicList.get(currentPlayIndex));
//                        break;
//                    case SHUFFLE_PLAYBACK:
//                        Random random = new Random();
//                        int index = random.nextInt(musicList.size());
//                        playMusic(musicList.get(index));
//                        break;
//                    default:
//                        break;
//                }
            }
        }

        @Override
        public void seekTo (int msec) throws RemoteException {
            synchronized (MusicBean.class) {
                mediaPlayer.seekTo(msec);
            }
        }

        @Override
        public boolean isLooping () throws RemoteException {
            synchronized (MusicBean.class) {
                return mediaPlayer.isLooping();
            }
        }

        @Override
        public boolean isPlaying () throws RemoteException {
            synchronized (MusicBean.class) {
                return MessagePreferences.getInstance().isPlaying();
            }
        }

        @Override
        public int position () throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public int getDuration () throws RemoteException {
            synchronized (MusicBean.class) {
                return mediaPlayer.getDuration();
            }
        }

        @Override
        public int getCurrentPosition () throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public List<MusicBean> getPlayList () throws RemoteException {
            synchronized (MusicBean.class) {
                return musicList;
            }
        }

        @Override
        public MusicBean getCurrentMusic () throws RemoteException {
            synchronized (MusicBean.class) {
                return MessagePreferences.getInstance().getCurrentMusic();
            }
        }

        @Override
        public void removeFromQueue (int position) throws RemoteException {

        }

        @Override
        public void clearQueue () throws RemoteException {

        }

        @Override
        public void showDesktopLyric (boolean show) throws RemoteException {

        }

        @Override
        public int audioSessionId () throws RemoteException {
            return 0;
        }

        @Override
        public void release () throws RemoteException {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    };

}
