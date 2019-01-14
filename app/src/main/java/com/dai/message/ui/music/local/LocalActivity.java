package com.dai.message.ui.music.local;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.dai.message.R;
import com.dai.message.base.BaseActivity;
import com.dai.message.util.Key;

/**
 * created by Administrator on 2018/12/27 17:23
 */
public class LocalActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            LocalFragment localFragment = LocalFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putBinder(Key.IBINDER, getIntent().getBundleExtra(Key.IBINDER).getBinder(Key.IBINDER));
            localFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, localFragment)
                    .commitNow();
        }
    }

}
