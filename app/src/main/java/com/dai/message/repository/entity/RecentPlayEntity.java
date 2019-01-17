package com.dai.message.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.dai.message.bean.Music;

/**
 * 最近播放歌曲实体类
 * <p>
 * created by dht on 2019/1/17 15:06
 */
//@Entity(tableName = "recent_play", indices = {@Index("recent_id")})
@Entity(tableName = "recent_play", indices = {@Index("recent_id"), @Index("id")})
public class RecentPlayEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recent_id")
    public int id;

    /*歌曲名称*/
    @ColumnInfo(name = "song_name")
    public String songName;

    /*用户id*/
    @ColumnInfo(name = "person_id")
    public long personId;

    /*播放总次数*/
    @ColumnInfo(name = "play_total")
    public int playTotal;

    /*最近播放时间*/
    @ColumnInfo(name = "play_time")
    public long playTime;


    /*最近播放次数*/
    @ColumnInfo(name = "recent_play_count")
    public int playCount;

    @Embedded()
    public Music music;
}
