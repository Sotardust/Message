package com.dht.databaselib.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dht.databaselib.bean.app.DownloadBean;
import com.dht.databaselib.bean.music.CloudMusicBean;

import java.util.List;

/**
 * 云盘数据
 * <p>
 * created by dht on 2019/1/17 15:17
 */
@Dao
public interface CloudMusicDao {

    /**
     * 像数据表中插入CloudMusicBean数据
     *
     * @param entities CloudMusicBean实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCloudMusicEntities(List<CloudMusicBean> entities);

    /**
     * 像数据表中插入CloudMusicBean数据
     *
     * @param entity CloudMusicBean实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCloudMusicEntity(CloudMusicBean entity);


    /**
     * 更新下载状态
     *
     * @param state    下载状态
     * @param songName 歌曲名称
     */
    @Query("update download set state=:state where name = :songName")
    void updateDownloadState(int state, String songName);

    /**
     * 查找正在下载的数据音乐数据
     *
     * @return MusicBean 实体集合
     */
    @Query("select * from download where state != 2  ")
    List<DownloadBean> getDownloadingList();


    /**
     * 查找下载完成的音乐数据
     *
     * @return MusicBean 实体集合
     */
    @Query("select * from download where state = 2 ")
    List<DownloadBean> getDownloadedList();


}
