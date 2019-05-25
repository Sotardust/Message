package com.dai.message.util;

import android.arch.persistence.room.TypeConverter;

/**
 * created by Administrator on 2018/11/19 15:36
 */
public enum CallType {
    //接听
    ANSWER,
    //拨打
    DIAL,
    //未接
    MISSED,
    //拒接
    REJECT,
    //其他
    OTHER;

    @TypeConverter
    public static CallType getCallType (int index) {
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
