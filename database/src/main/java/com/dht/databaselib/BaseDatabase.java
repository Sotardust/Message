package com.dht.databaselib;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.bean.music.RecentPlayBean;
import com.dht.databaselib.dao.MusicDao;
import com.dht.databaselib.dao.RecentPlayDao;


/**
 * created by dht on 2018/7/4 16:37
 *
 * @author Administrator
 */
@Database(entities = {MusicBean.class, RecentPlayBean.class}, version = 1)
public abstract class BaseDatabase extends RoomDatabase {

    private static BaseDatabase INSTANCE;

    /**
     * 操作数据库AllCallsDao抽象接口
     */
//    public abstract AllCallsDao getAllCallsDao ();

    /**
     * 操作数据库MusicDao抽象接口
     */
    public abstract MusicDao getMusicDao ();

    /**
     * 操作数据库MusicDao抽象接口
     */
    public abstract RecentPlayDao getRecentPlayDao ();
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

    public static BaseDatabase getInstance (Context context) {
        if (INSTANCE == null) {
            synchronized (BaseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BaseDatabase.class, "message.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
