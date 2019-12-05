package com.dht.message.repository;

import android.app.Application;

import com.dht.databaselib.BaseDatabase;
import com.dht.databaselib.bean.app.DownloadBean;
import com.dht.databaselib.dao.DownloadDao;

/**
 * created by dht on 2019/3/11 18:17
 */
public class DownloadRepository {

    private DownloadDao downloadDao;

    public DownloadRepository(Application application) {

        downloadDao = BaseDatabase.getInstance(application.getApplicationContext()).getDownloadDao();
    }

    public void addDownloadMusic(DownloadBean music) {
        downloadDao.addDownloadEntity(music);
    }
}
