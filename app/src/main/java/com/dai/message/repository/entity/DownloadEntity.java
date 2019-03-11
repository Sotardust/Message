package com.dai.message.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * created by dht on 2019/3/11 18:27
 */
@Entity(tableName = "download")
public class DownloadEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    /*歌名*/
    @ColumnInfo(name = "name")
    public String name;

    /*歌曲路径*/
    @ColumnInfo(name = "path")
    public String path;

    /*歌手*/
    @ColumnInfo(name = "author")
    public String author;


    /*下载状态：0：下载中 ，1：暂停 ，2：完成 */
    @ColumnInfo(name = "state")
    public int state;


}
