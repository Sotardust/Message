package com.dht.baselib.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dht.baselib.R;


/**
 * created by dht on 2018/7/3 16:45
 *
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取布局文件 Id
     *
     * @return Int
     */
//    protected abstract int getLayoutId ();

    /**
     * 向Activity中添加 添加Fragment
     */
//    protected abstract void addFragmentCommitNow ();

}
