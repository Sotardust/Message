package com.dai.message.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    /*用户名*/
    @ColumnInfo(name = "name")
    private String name;

    /*时间戳作为用户唯一ID*/
    @ColumnInfo(name = "timestamp")
    private String timestamp;
    /*手机号*/
    @ColumnInfo(name = "number")
    private String number;
}
