package com.dht.music.repository

import android.app.Application
import android.util.Log
import com.dht.databaselib.BaseDatabase
import com.dht.databaselib.dao.MusicDao

/**
 *
 *  created by dht on 2020/1/6 18:26
 */
class CloudDiskRepository {

    private val TAG = "MusicRepository"

    private var musicDao: MusicDao? = null

    fun CloudDiskRepository(application: Application) {
        val appDatabase = BaseDatabase.getInstance(application.applicationContext)
        musicDao = appDatabase.musicDao
        Log.d(TAG, "MusicRepository: musicDao = $musicDao")
    }
}