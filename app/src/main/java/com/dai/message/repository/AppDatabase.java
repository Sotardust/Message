package com.dai.message.repository;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.dai.message.bean.Music;
import com.dai.message.repository.dao.AllCallsDao;
import com.dai.message.repository.dao.MusicDao;
import com.dai.message.repository.dao.RecentPlayDao;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dai.message.repository.entity.RecentPlayEntity;

/**
 * created by dht on 2018/7/4 16:37
 */
@Database(entities = {AllCallsEntity.class, Music.class, RecentPlayEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    /**
     * 操作数据库AllCallsDao抽象接口
     */
    public abstract AllCallsDao getAllCallsDao();

    /**
     * 操作数据库MusicDao抽象接口
     */
    public abstract MusicDao getMusicDao();

    /**
     * 操作数据库MusicDao抽象接口
     */
    public abstract RecentPlayDao getRecentPlayDao();
//
//    /**
//     * 操作数据库ContactDao抽象接口
//     */
//    public abstract ContactDao contactDao();
//
//    /**
//     * 操作数据库HistoryDao抽象接口
//     */
//    public abstract HistoryDao historyDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (AppDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "message.db")
                            .build();
            }
        return INSTANCE;
    }
}
