package com.dai.message.util;

import android.arch.persistence.room.TypeConverter;


/**
 * created by Administrator on 2018/11/19 16:41
 */
public class CallTypeConverter {

    @TypeConverter
    public static CallType toCallType(int index) {
        switch (index) {
            case 1:
                return CallType.ANSWER;
            case 2:
                return CallType.DIAL;
            case 3:
                return CallType.MISSED;
            case 5:
                return CallType.REJECT;
            default:
                return CallType.OTHER;
        }
    }

    @TypeConverter
    public static int toInteger(CallType type) {
        switch (type) {
            case ANSWER:
                return 1;
            case DIAL:
                return 2;
            case MISSED:
                return 3;
            case REJECT:
                return 5;
            default:
                return 0;
        }
    }
}
