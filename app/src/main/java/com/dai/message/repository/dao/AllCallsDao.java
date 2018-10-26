package com.dai.message.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.dai.message.repository.entity.AllCallsEntity;

/**
 * 对AllCallsEntity 存入数据库 提供 添加，更新 查询 删除功能
 */
@Dao
public interface AllCallsDao {

    /**
     * 向allCalls表中插入AllCallsEntity数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllCallsEntity(AllCallsEntity entity);
}
