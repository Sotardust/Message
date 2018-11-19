package com.dai.message.util;

import android.arch.persistence.room.TypeConverter;

/**
 * created by Administrator on 2018/11/19 15:36
 */
public enum CallType {

    ANSWER,//接听
    DIAL,//拨打
    MISSED,//未接
    REJECT,//拒接
    OTHER;//其他

    @TypeConverter
    public static CallType getCallType(int index) {
        switch (index) {
            case 1:
                return ANSWER;
            case 2:
                return DIAL;
            case 3:
                return MISSED;
            case 5:
                return REJECT;
            default:
                return OTHER;
        }
    }
}
