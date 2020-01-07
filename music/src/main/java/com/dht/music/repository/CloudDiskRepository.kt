package com.dht.music.repository

import android.app.Application
import com.dht.databaselib.BaseDatabase
import com.dht.databaselib.bean.music.CloudMusicBean
import com.dht.databaselib.dao.CloudMusicDao
import com.dht.databaselib.preferences.MessagePreferences

/**
 *
 *  created by dht on 2020/1/6 18:26
 */
class CloudDiskRepository constructor(application: Application) {

    private val TAG = "MusicRepository"

    private var cloudMusicDao: CloudMusicDao? = null

    init {
        val appDatabase = BaseDatabase.getInstance(application.applicationContext)
        cloudMusicDao = appDatabase.cloudMusicDao
    }

    /**
     * 获取云盘音乐列表
     */
    fun getMusicList(): List<CloudMusicBean>? = cloudMusicDao?.getCloudMusicList(MessagePreferences.INSTANCE.personId)

    /**
     * 向库中插入云盘音乐
     */
    fun insertMusic(beans: List<CloudMusicBean>) {
        cloudMusicDao?.addCloudMusicEntities(beans)
    }

}