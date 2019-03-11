package com.dai.message.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dai.message.bean.Music;

import java.util.List;

/**
 * created by dht on 2019/1/17 15:17
 */
@Dao
public interface DownloadDao {

    /**
     * 像数据表中插入Music数据
     *
     * @param entities Music实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDownloadEntities(List<Music> entities);

    /**
     * 像数据表中插入Music数据
     *
     * @param entity Music实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDownloadEntity(Music entity);


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
     * @return Music 实体集合
     */
    @Query("select * from download where state != 2  ")
    List<Music> getDownloadingList();


    /**
     * 查找下载完成的音乐数据
     *
     * @return Music 实体集合
     */
    @Query("select * from download where state = 2 ")
    List<Music> getDownloadedList();


}
