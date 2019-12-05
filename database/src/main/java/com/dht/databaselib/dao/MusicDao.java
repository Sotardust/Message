package com.dht.databaselib.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dht.databaselib.bean.music.MusicBean;

import java.util.List;

/**
 * 对MusicEntity 存入数据库 提供 添加，更新 查询 删除功能
 * <p>
 * created by dht on 2019/1/11 09:57
 *
 * @author Administrator
 */

@Dao
public interface MusicDao {

    /**
     * 像数据表中插入music数据
     *
     * @param music Music实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMusics (List<MusicBean> music);

    /**
     * 像数据表中插入music数据
     *
     * @param music Music实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMusic (MusicBean music);

    /**
     * 查找所有音乐数据
     *
     * @return Music 实体集合
     */
    @Query("select * from music")
    List<MusicBean> getAllMusics ();

    /**
     * 根据音乐名称删除数据表中对应数据
     *
     * @param name 歌名
     */
    @Query("delete from music where name = :name")
    void deleteMusic (String name);

    /**
     * 根据歌曲名称查找对应数据
     *
     * @param name 歌名
     * @return Music实体类
     */
    @Query("select * from music where name  = :name")
    MusicBean getMusic (String name);


    /**
     * 以id为索引统计music总个数
     *
     * @return 本地音乐个数
     */
    @Query("select count(code) from music ")
    int getMusicTotal ();

    /**
     * 获取数据表中所有 歌曲名称
     *
     * @return 歌名集合
     */
    @Query("select name from music")
    List<String> getAllNames ();

}
