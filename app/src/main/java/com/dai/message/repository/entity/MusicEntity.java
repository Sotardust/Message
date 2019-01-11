package com.dai.message.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * created by Administrator on 2019/1/11 09:47
 */

@Entity(tableName = "music")
public class MusicEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    /*歌名*/
    @ColumnInfo(name = "name")
    public String name;

    /*歌曲路径*/
    @ColumnInfo(name = "path")
    public String path;

    /*歌手*/
    @ColumnInfo(name = "author")
    public String author;

    /*歌曲对应图像*/
    @ColumnInfo(name = "avatar")
    public String avatar;

    /*歌词*/
    @ColumnInfo(name = "lyrics")
    public String lyrics;

    /*歌曲类型*/
    @ColumnInfo(name = "type")
    public String type;


}
