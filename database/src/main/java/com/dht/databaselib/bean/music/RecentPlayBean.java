package com.dht.databaselib.bean.music;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


/**
 * 最近播放歌曲实体类
 * <p>
 * created by dht on 2019/1/17 15:06\
 *
 * @author Administrator
 * @Entity(tableName = "recent_play", indices = {@Index("recent_id")})
 */

@Entity(tableName = "recent_play", indices = {@Index("recent_id")})
public class RecentPlayBean {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recent_id")
    public int id;
    /**
     * 歌曲名称
     */
    @ColumnInfo(name = "song_name")
    public String songName;

    /**
     * 用户id
     */
    @ColumnInfo(name = "person_id")
    public long personId;

    /**
     * 播放总次数
     */
    @ColumnInfo(name = "play_total")
    public int playTotal;

    /**
     * 歌曲最近播放时间名称
     */
    @ColumnInfo(name = "play_time")
    public long playTime;


    /**
     * 最近播放次数
     */
    @ColumnInfo(name = "recent_play_count")
    public int playCount;

    @Embedded()
    public MusicBean music;
}
