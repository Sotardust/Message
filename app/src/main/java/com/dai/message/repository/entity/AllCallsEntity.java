package com.dai.message.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * 记录所有通话记录
 * created by Administrator on 2018/10/24 18:32
 */
@Entity(tableName = "allCalls")
public class AllCallsEntity {

    /*姓名*/
    @ColumnInfo(name = "name")
    private String name;

    /*拨号方手机号*/
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    private String callNumber;

    /*接收方手机号*/
    @ColumnInfo(name = "receiver_number")
    private String receiverNumber;

    /*地址*/
    @ColumnInfo(name = "address")
    private String address;

    /*拨打时间*/
    @ColumnInfo(name = "call_time")
    private String callTime;

    /*通话总时长*/
    @ColumnInfo(name = "total_time")
    private int TotalTime = 0;

    /*单次通话时长*/
    @ColumnInfo(name = "single_name")
    private int singleTime;

    /*类型*/
    @ColumnInfo(name = "type")
    private String type;

    /*拨打类型*/
    @ColumnInfo(name = "call_type")
    private int callType;

    /*拨打次数*/
    @ColumnInfo(name = "dial_times")
    private int dialTimes = 0;

    /*接通次数*/
    @ColumnInfo(name = "receiver_times")
    private int receiverTimes = 0;

    /*未接次数*/
    @ColumnInfo(name = "missed_times")
    private int missedTimes = 0;

    /*拒接次数*/
    @ColumnInfo(name = "refuse_times")
    private int refuseTimes = 0;

    /*是否发送成功*/
    @ColumnInfo(name = "is_send_successful", index = false)
    private boolean isSendSuccessful;

    public AllCallsEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public int getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(int totalTime) {
        TotalTime = totalTime;
    }

    public int getSingleTime() {
        return singleTime;
    }

    public void setSingleTime(int singleTime) {
        this.singleTime = singleTime;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public int getDialTimes() {
        return dialTimes;
    }

    public void setDialTimes(int dialTimes) {
        this.dialTimes = dialTimes;
    }

    public int getReceiverTimes() {
        return receiverTimes;
    }

    public void setReceiverTimes(int receiverTimes) {
        this.receiverTimes = receiverTimes;
    }

    public int getMissedTimes() {
        return missedTimes;
    }

    public void setMissedTimes(int missedTimes) {
        this.missedTimes = missedTimes;
    }

    public boolean isSendSuccessful() {
        return isSendSuccessful;
    }

    public void setSendSuccessful(boolean sendSuccessful) {
        isSendSuccessful = sendSuccessful;
    }

    public int getTimes() {
        return getDialTimes() + getReceiverTimes() + getMissedTimes() + getRefuseTimes();
    }

    public int getRefuseTimes() {
        return refuseTimes;
    }

    public void setRefuseTimes(int refuseTimes) {
        this.refuseTimes = refuseTimes;
    }
}
