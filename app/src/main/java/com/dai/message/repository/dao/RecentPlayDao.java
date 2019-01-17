package com.dai.message.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dai.message.repository.entity.RecentPlayEntity;

import java.util.List;

/**
 * created by dht on 2019/1/17 15:17
 */
@Dao
public interface RecentPlayDao {

    /**
     * 像数据表中插入RecentPlayEntity数据
     *
     * @param entities RecentPlayEntity实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRecentPlayEntities(List<RecentPlayEntity> entities);

    /**
     * 像数据表中插入RecentPlayEntity数据
     *
     * @param entity RecentPlayEntity实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRecentPlayEntity(RecentPlayEntity entity);


    /**
     * 更新RecentPlayEntity
     *
     * @param entity RecentPlayEntity
     */
    @Update()
    void updateRecentPlayEntity(RecentPlayEntity entity);

    /**
     * 根据id 更新对应数据
     *
     * @param id        主键id
     * @param playTime  最近一次播放时间
     * @param playCount 最近播放次数 一周内
     * @param playTotal 所有播放次数
     */
    @Query("update recent_play set play_time=:playTime,recent_play_count=:playCount,play_total=:playTotal where recent_id = :id")
    void updateRecentPlayEntity(int id, long playTime, int playCount, int playTotal);

    /**
     * 查找人员对应所有最近播放音乐数据
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play where person_id =:personId")
    List<RecentPlayEntity> getPersonRecentPlayEntity(long personId);

    /**
     * 根据音乐名称删除数据表中对应数据
     *
     * @param name 歌名
     */
    @Query("delete from recent_play where name = :name")
    void deleteRecentPlayEntity(String name);


    /**
     * 以id为索引统计最近播放总个数
     *
     * @return 本地最近播放音乐个数
     */
    @Query("select count(recent_id) from recent_play where person_id =:personId ")
    int getRecentPlayTotal(long personId);

    /**
     * 查找所有最近播放音乐数据
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play ")
    List<RecentPlayEntity> getRecentPlayEntity();

    /**
     * 获取数据表中所有 歌曲名称
     *
     * @return 歌名集合
     */
    @Query("select name from recent_play where person_id =:personId")
    List<String> getPersonSongNames(long personId);
}
