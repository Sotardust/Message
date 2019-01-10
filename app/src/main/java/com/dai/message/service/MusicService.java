package com.dai.message.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.dai.message.bean.IMusicAidlInterface;
import com.dai.message.bean.Music;

import java.util.List;


/**
 * created by dht on 2019/1/10 16:15
 */

public class MusicService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return aidlInterface;
    }

    IMusicAidlInterface.Stub aidlInterface = new IMusicAidlInterface.Stub() {
        @Override
        public void setDataSource(String path) throws RemoteException {

        }

        @Override
        public void nextPlay(Music music) throws RemoteException {

        }

        @Override
        public void playMusic(Music music) throws RemoteException {

        }

        @Override
        public void playPlaylist(List<Music> playlist, int id, String pid) throws RemoteException {

        }

        @Override
        public void playPause() throws RemoteException {

        }

        @Override
        public void pause() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {

        }

        @Override
        public void playPrevious() throws RemoteException {

        }

        @Override
        public void playNext() throws RemoteException {

        }

        @Override
        public void seekTo(int msec) throws RemoteException {

        }

        @Override
        public boolean isLooping() throws RemoteException {
            return false;
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return false;
        }

        @Override
        public int position() throws RemoteException {
            return 0;
        }

        @Override
        public int getDuration() throws RemoteException {
            return 0;
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return 0;
        }

        @Override
        public String getSongName() throws RemoteException {
            return null;
        }

        @Override
        public String getSongArtist() throws RemoteException {
            return null;
        }

        @Override
        public Music getPlayingMusic() throws RemoteException {
            return null;
        }

        @Override
        public List<Music> getPlayList() throws RemoteException {
            return null;
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

        }
    };
}
