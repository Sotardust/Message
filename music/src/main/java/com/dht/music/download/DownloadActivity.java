package com.dht.music.download;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.dht.baselib.base.BaseActivity;
import com.dht.baselib.base.BaseFragmentPageAdapter;
import com.dht.baselib.base.BaseViewPager;
import com.dht.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dht on 2019/01/07 18:23:23
 *
 * @author Administrator
 */
public class DownloadActivity extends BaseActivity {


    private String[] titles = {"正在下载", "下载完成"};

    private TabLayout tabLayout;

    private BaseViewPager baseViewPager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        bindViews();
        initData();
    }

    /**
     * 绑定视图View
     */
    private void bindViews () {
        tabLayout = findViewById(R.id.tabLayout);
        baseViewPager = findViewById(R.id.baseViewPager);
    }

    /**
     * 初始化基础数据
     */
    private void initData () {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(DownloadingFragment.newInstance());
        fragmentList.add(DownloadedFragment.newInstance());
        baseViewPager.setAdapter(new BaseFragmentPageAdapter(getSupportFragmentManager(), fragmentList, titles));
        tabLayout.setupWithViewPager(baseViewPager);
    }
}