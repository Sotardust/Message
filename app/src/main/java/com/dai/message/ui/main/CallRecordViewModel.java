package com.dai.message.ui.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dai.message.callback.CallBack;
import com.dai.message.repository.AllCallsRepository;
import com.dai.message.repository.entity.AllCallsEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * created by Administrator on 2018/10/26 09:52
 */
public class CallRecordViewModel extends AndroidViewModel {

    private static final String TAG = "CallRecordViewModel";

    protected Application application;
    protected SimpleDateFormat format;
    private ArrayList<AllCallsEntity> allCallList;

    protected AllCallsRepository repository;

    private static boolean IS_FIRST = true;


    public CallRecordViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        repository = new AllCallsRepository(application);
        allCallList = new ArrayList<>();
        if (IS_FIRST) {
            findLocalAllCalls();
            setAllCallsOtherData();
        }
    }

    /**
     * 查找全部通话记录
     */

    @SuppressLint({"MissingPermission", "HardwareIds", "NewApi"})
    private void findLocalAllCalls() {

        ContentResolver cr = application.getApplicationContext().getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        TelephonyManager tm = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        String number1 = null;
        try {
            if (tm != null) {
                number1 = tm.getLine1Number();
                Log.d(TAG, " number1 = " + number1 +
                        " 手机号个数：" + tm.getPhoneCount() +
                        " getGroupIdLevel1：" + tm.getGroupIdLevel1() +
                        " getSimSerialNumber：" + tm.getSimSerialNumber() +
                        " getSubscriberId：" + tm.getSubscriberId() +
                        " getDeviceSoftwareVersion：" + tm.getDeviceSoftwareVersion() +
                        " getImei(1)：" + tm.getImei(1) +
                        " getImei(2)：" + tm.getImei(2) +
                        " getMeid(1)：" + tm.getMeid(1) +
                        " getMeid(2)：" + tm.getMeid(2) +
                        " getSimState(1)：" + tm.getSimState(1) +
                        " getSimState(2)：" + tm.getSimState(2) +
                        " getDeviceId(1)：" + tm.getDeviceId(1) +
                        " getDeviceId(2)：" + tm.getDeviceId(2)
                );
            }
            @SuppressLint("InlinedApi")
            String[] projection = new String[]{CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE,
                    CallLog.Calls.TYPE, CallLog.Calls.NUMBER_PRESENTATION, CallLog.Calls.DURATION, CallLog.Calls.IS_READ};

            Cursor cursor = cr.query(uri, projection, null, null, null);
            if (cursor == null) return;
            while (cursor.moveToNext()) {
                allCallList.add(getAllCalls(cursor));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "findLocalAllCalls: e", e);
        }


    }

    /**
     * 填充实体类
     *
     * @param cursor Cursor
     * @return AllCalls实体数据
     */
    private AllCallsEntity getAllCalls(Cursor cursor) {
        AllCallsEntity allCallsEntity = new AllCallsEntity();
        allCallsEntity.setName(cursor.getString(0) == null ? "未知" : cursor.getString(0));
        allCallsEntity.setCallNumber(cursor.getString(1));
        allCallsEntity.setCallTime(format.format(cursor.getLong(2)));
        allCallsEntity.setCallType(cursor.getInt(3));
        allCallsEntity.setSingleTime(cursor.getInt(5));
        return allCallsEntity;
    }

    /**
     * type 1/2/3/4/5 接听/拨打/未接//拒接
     * 设置其他AllCalls数据
     */
    private void setAllCallsOtherData() {
        for (AllCallsEntity allCallsEntity : allCallList) {
            switch (allCallsEntity.getCallType()) {
                case 1:
                    allCallsEntity.setType("接听");
                    allCallsEntity.setReceiverTimes(allCallsEntity.getReceiverTimes() + 1);
                    break;
                case 2:
                    allCallsEntity.setType("拨打");
                    allCallsEntity.setDialTimes(allCallsEntity.getDialTimes() + 1);
                    break;
                case 3:
                    allCallsEntity.setMissedTimes(allCallsEntity.getMissedTimes() + 1);
                    allCallsEntity.setType("未接");
                    break;
                case 4:
                    break;
                case 5:
                    allCallsEntity.setType("拒接");
                    allCallsEntity.setRefuseTimes(allCallsEntity.getRefuseTimes() + 1);
                    break;
                case 6:
                    break;
            }
            allCallsEntity.setTotalTime(allCallsEntity.getTotalTime() + allCallsEntity.getSingleTime());
        }
    }

    /**
     * 去重 自定义0 为获取全部通话记录
     * 1/2/3/4/5 接听/拨打/未接//拒接
     *
     * @return AllCalls实体集合
     */
    protected void distinctAllCalls(CallBack<List<AllCallsEntity>> callBack) {

        if (IS_FIRST) {
            final ArrayList<String> numberList = new ArrayList<>();
            final HashMap<String, AllCallsEntity> allCallsHM = new HashMap<>();
            for (AllCallsEntity allCallsEntity : allCallList) {
                if (numberList.contains(allCallsEntity.getCallNumber())) {
                    AllCallsEntity calls = allCallsHM.get(allCallsEntity.getCallNumber());
                    calls.setTotalTime(calls.getSingleTime() + allCallsEntity.getSingleTime());
                    calls.setRefuseTimes(calls.getRefuseTimes() + allCallsEntity.getRefuseTimes());
                    calls.setMissedTimes(calls.getMissedTimes() + allCallsEntity.getMissedTimes());
                    calls.setReceiverTimes(calls.getReceiverTimes() + allCallsEntity.getReceiverTimes());
                    calls.setDialTimes(calls.getDialTimes() + allCallsEntity.getDialTimes());
                } else {
                    numberList.add(allCallsEntity.getCallNumber());
                    allCallsHM.put(allCallsEntity.getCallNumber(), allCallsEntity);
                }
            }
            for (String s : allCallsHM.keySet()) {
                repository.addAllCallsEntity(allCallsHM.get(s));
            }
            IS_FIRST = !IS_FIRST;
        }
        repository.getAllCallsEntities(callBack);
    }
}
