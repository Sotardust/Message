package com.dai.message.ui.music.download;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.dai.message.R;
import com.dai.message.adapter.BaseFragmentPageAdapter;
import com.dai.message.base.BaseActivity;
import com.dai.message.base.BaseViewPager;
import com.dai.message.ui.view.TopTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dht on 2019/01/07 18:23:23
 */
public class DownloadActivity extends BaseActivity {


    private String[] titles = {"正在下载", "下载完成"};

    private TabLayout tabLayout;
    private TopTitleView topTitleView;

    private BaseViewPager baseViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        bindViews();
        initData();
    }

    /**
     * 绑定视图View
     */
    private void bindViews() {
        tabLayout = findViewById(R.id.tabLayout);
        baseViewPager = findViewById(R.id.baseViewPager);
        topTitleView = findViewById(R.id.download_top_title);

    }

    /**
     * 初始化基础数据
     */
    private void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(DownloadingFragment.newInstance());
        fragmentList.add(DownloadedFragment.newInstance());
        baseViewPager.setAdapter(new BaseFragmentPageAdapter(getSupportFragmentManager(), fragmentList, titles));
        tabLayout.setupWithViewPager(baseViewPager);

        topTitleView.updateView(this,getString(R.string.download_manage));
    }
}