package com.dai.message.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dai.message.repository.entity.AllCallsEntity;

import java.util.List;

/**
 * 对AllCallsEntity 存入数据库 提供 添加，更新 查询 删除功能
 *
 * @author Administrator
 */
@Dao
public interface AllCallsDao {

    /**
     * 向allCalls表中插入AllCallsEntity数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllCallsEntity (AllCallsEntity entity);

    /**
     * 查找所有通话记录数据
     *
     * @return AllCallsEntity 实体集合
     */
    @Query("select * from allCalls")
    List<AllCallsEntity> findAllCallsEntities ();

    /**
     * 根据类型查找对应的通话记录数据
     *
     * @return AllCallsEntity 实体集合
     */
    @Query("select * from allCalls where call_type = :callType")
    List<AllCallsEntity> findCallsEntities (String callType);


}
