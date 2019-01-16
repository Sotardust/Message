package com.dai.message;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.dai.message.base.BaseActivity;
import com.dai.message.callback.LocalCallback;
import com.dai.message.ui.login.LoginActivity;
import com.dai.message.ui.login.LoginFragment;
import com.dai.message.util.file.FileUtil;

import java.util.ArrayList;

/**
 * created by Administrator on 2018/10/29 14:44
 */
public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";

    private static final int REQUEST_CODE = 1;

    private static final String[] requestPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

    private WelcomeModel welcomeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeModel = new WelcomeModel(getApplication());
        checkSelfPermission();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, " requestCode = [" + requestCode + "], permissions = [" + permissions + "], grantResults = [" + grantResults + "]");
        if (requestCode == REQUEST_CODE) {
            //请求权限后必须创建日志文件
            FileUtil.createLogFile();
        }

        welcomeModel.initDatabaseData(new LocalCallback<String>() {
            @Override
            public void onChangeData(String data) {
                Log.d(TAG, "onChangeData: path = " + data);
            }
        }, new LocalCallback<String>() {
            @Override
            public void onSuccessful() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * 检查权限并申请
     */
    private void checkSelfPermission() {
        ArrayList<String> permissionList = new ArrayList<>();
        for (String permission : requestPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), REQUEST_CODE);
    }
}
