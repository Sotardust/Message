package com.dht.databaselib.bean.music;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Administrator
 */
@Entity(tableName = "cloud_music")
public class CloudMusicBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * 人员ID
     */
    public int personId;
    /**
     * 音乐对应唯一ID
     */
    public long musicId;
    /**
     * 音乐名称
     */
    public String name;
    /**
     * 存放路径
     */
    public String path;
    /**
     * 音乐类型
     */
    public String type;
    /**
     * 音乐时长
     */
    public int duration;
}
