package com.dai.message.repository;

import android.app.Application;

import com.dai.message.bean.Music;
import com.dai.message.repository.dao.DownloadDao;

/**
 * created by dht on 2019/3/11 18:17
 */
public class DownloadRepository {

    private DownloadDao downloadDao;

    public DownloadRepository(Application application) {

        downloadDao = AppDatabase.getInstance(application.getApplicationContext()).getDownloadDao();
    }

    public void addDownloadMusic(Music music) {
        downloadDao.addDownloadEntity(music);
    }
}
