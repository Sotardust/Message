package com.dai.message.ui.main;


import android.Manifest;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dai.message.repository.model.AllCalls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class AllCallsViewModel extends AndroidViewModel {

    private static final String TAG = "AllCallsViewModel";
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCalls>> mAllCallsList;

    private Application application;
    private SimpleDateFormat format;
    private ArrayList<AllCalls> allCallList = new ArrayList<>();

    public AllCallsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        allCallList = new ArrayList<>();
    }


    public MutableLiveData<ArrayList<AllCalls>> getAllCallsList() {
        if (mAllCallsList == null) {
            mAllCallsList = new MutableLiveData<>();
            findLocalAllCalls();
            setAllCallsOtherData();
            mAllCallsList.setValue(distinctAllCalls());
        }
        return mAllCallsList;
    }

    /**
     * 查找全部通话记录
     *
     * @return
     */
    private void findLocalAllCalls() {

        if (allCallList.size() != 0) allCallList.clear();

        ContentResolver cr = application.getApplicationContext().getContentResolver();
        Uri uri = Calls.CONTENT_URI;

        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(application, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(application, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }
        TelephonyManager tm = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber1 = null;
        if (tm != null) {
            phoneNumber1 = tm.getLine1Number();
        }
        Log.d(TAG, "findLocalAllCalls: phoneNumber1 = " + phoneNumber1);
        String[] projection = new String[]{Calls.CACHED_NAME, Calls.NUMBER, Calls.DATE,
                Calls.TYPE, Calls.NUMBER_PRESENTATION, Calls.DURATION, Calls.IS_READ};


        Cursor cursor = cr.query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            Log.d(TAG, "findLocalAllCalls: name =" + cursor.getString(0) + ", number = " + cursor.getString(1) + ", type = " + cursor.getString(3) + ", date = " + format.format(cursor.getLong(2)) + ", duration = " + cursor.getLong(5));
            allCallList.add(getAllCalls(cursor));
        }
        cursor.close();

    }

    /**
     * 填充实体类
     *
     * @param cursor Cursor
     * @return AllCalls实体数据
     */
    private AllCalls getAllCalls(Cursor cursor) {
        AllCalls allCalls = new AllCalls();
        allCalls.setName(cursor.getString(0) == null ? "未知" : cursor.getString(0));
        allCalls.setCallNumber(cursor.getString(1));
        allCalls.setCallTime(format.format(cursor.getLong(2)));
        allCalls.setCallType(cursor.getInt(3));
        allCalls.setSingleTime(cursor.getInt(5));
        return allCalls;
    }

    /**
     * type 1/2/3/4/5 接听/拨打/未接//拒接
     * 设置其他AllCalls数据
     */
    private void setAllCallsOtherData() {
        for (AllCalls allCalls : allCallList) {
            switch (allCalls.getCallType()) {
                case 1:
                    allCalls.setType("接听");
                    allCalls.setReceiverTimes(allCalls.getReceiverTimes() + 1);
                    break;
                case 2:
                    allCalls.setType("拨打");
                    allCalls.setDialTimes(allCalls.getDialTimes() + 1);
                    break;
                case 3:
                    allCalls.setMissedTimes(allCalls.getMissedTimes() + 1);
                    allCalls.setType("未接");
                    break;
                case 4:
                    break;
                case 5:
                    allCalls.setType("拒接");
                    allCalls.setRefuseTimes(allCalls.getRefuseTimes() + 1);
                    break;
                case 6:
                    break;
            }
            allCalls.setTotalTime(allCalls.getTotalTime() + allCalls.getSingleTime());
        }
    }

    /**
     * 去重
     *
     * @return AllCalls实体集合
     */
    private ArrayList<AllCalls> distinctAllCalls() {
        final ArrayList<String> numberList = new ArrayList<>();
        final ArrayList<AllCalls> list = new ArrayList<>();
        final HashMap<String, AllCalls> allCallsHM = new HashMap<>();
        for (AllCalls allCalls : allCallList) {
            if (numberList.contains(allCalls.getCallNumber())) {
                AllCalls calls = allCallsHM.get(allCalls.getCallNumber());
                calls.setTotalTime(calls.getSingleTime() + allCalls.getSingleTime());
                calls.setRefuseTimes(calls.getRefuseTimes() + allCalls.getRefuseTimes());
                calls.setMissedTimes(calls.getMissedTimes() + allCalls.getMissedTimes());
                calls.setReceiverTimes(calls.getReceiverTimes() + allCalls.getReceiverTimes());
                calls.setDialTimes(calls.getDialTimes() + allCalls.getDialTimes());
            } else {
                numberList.add(allCalls.getCallNumber());
                allCallsHM.put(allCalls.getCallNumber(), allCalls);
            }
        }
        for (String s : allCallsHM.keySet()) {
            list.add(allCallsHM.get(s));
        }
        return list;
    }
}