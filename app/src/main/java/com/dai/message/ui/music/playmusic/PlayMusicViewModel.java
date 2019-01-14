package com.dai.message.ui.music.playmusic;

import android.app.Application;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.dai.message.base.BaseAndroidViewModel;
import com.dai.message.bean.Music;

import java.io.IOException;

public class PlayMusicViewModel extends BaseAndroidViewModel {

    private static final String TAG = "PlayMusicViewModel";

    public PlayMusicViewModel(@NonNull Application application) {
        super(application);
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playMusic(Music music) {

        try {
            mediaPlayer.setDataSource(music.path);
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
            mediaPlayer.setAudioAttributes(attrBuilder.build());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                    mp.start();
                }
            });
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Log.d(TAG, "onSeekComplete: mp" + mp.getCurrentPosition());
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    Log.d(TAG, "onCompletion: ");
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onError() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
                    return false;
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mediaPlayer.setOnDrmInfoListener(new MediaPlayer.OnDrmInfoListener() {
                    @Override
                    public void onDrmInfo(MediaPlayer mp, MediaPlayer.DrmInfo drmInfo) {
                        Log.d(TAG, "onDrmInfo() called with: mp = [" + mp + "], drmInfo = [" + drmInfo + "]");
                    }
                });
            }
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onInfo() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
                    return false;
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {

                    Log.d(TAG, "onBufferingUpdate() called with: mp = [" + mp + "], percent = [" + percent + "]");
                }
            });

//           Observable.interval(0,500,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
//                   .observeOn(AndroidSchedulers.mainThread())
//                   .subscribe(new Observer<Long>() {
//                       @Override
//                       public void onSubscribe(Disposable d) {
//
//                       }
//
//                       @Override
//                       public void onNext(Long aLong) {
//                           Log.d(TAG, "onNext: position = "+ mediaPlayer.getCurrentPosition());
//                           Log.d(TAG, "onNext: "+ aLong);
//                       }
//
//                       @Override
//                       public void onError(Throwable e) {
//
//                       }
//
//                       @Override
//                       public void onComplete() {
//
//                       }
//                   });
//            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

}
