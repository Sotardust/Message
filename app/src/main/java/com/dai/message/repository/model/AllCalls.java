package com.dai.message.repository.model;

/**
 * created by Administrator on 2018/10/24 18:32
 */
public class AllCalls {


    /*姓名*/
    private String name;

    /*拨号方手机号*/
    private String callNumber;

    /*接收方手机号*/
    private String receiverNumber;

    /*地址*/
    private String address;

    /*拨打时间*/
    private String callTime;

    /*通话总时长*/
    private int TotalTime = 0;

    /*单次通话时长*/
    private int singleTime;

    /*类型*/
    private String type;

    /*拨打类型*/
    private int callType;

    /*拨打次数*/
    private int dialTimes = 0;

    /*接通次数*/
    private int receiverTimes = 0;

    /*未接次数*/
    private int missedTimes = 0;

    /*拒接次数*/
    private int refuseTimes = 0;

    /*是否发送成功*/
    private boolean isSendSuccessful;

    public AllCalls() {
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
