package com.dai.message.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dai.message.R;
import com.dht.baselib.base.BaseFragmentPageAdapter;
import com.dai.message.callback.OnPageChangerCallback;
import com.dai.message.callback.TabLayoutCallback;
import com.dai.message.databinding.FragmentHomeBinding;
import com.dai.message.receiver.BaseReceiver;
import com.dai.message.receiver.SendLocalBroadcast;
import com.dai.message.ui.music.MusicFragment;
import com.dai.message.ui.news.NewsFragment;
import com.dht.music.view.MusicTitleView;
import com.dht.baselib.base.BaseFragment;
import com.dht.baselib.callback.LocalCallback;
import com.dht.databaselib.bean.music.IMusicAidlInterface;
import com.dht.music.MusicActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel mViewModel;
    private FragmentHomeBinding mBinding;

    private BaseReceiver receiver;
    private String[] titles = {"音乐", "新闻", "小说", "我的"};
    private int[] images = {R.drawable.tablayout_music_bg, R.drawable.tablayout_news_bg,
            R.drawable.tablayout_novel_bg, R.drawable.tablayout_setting_bg};

    private IMusicAidlInterface service;

    public static HomeFragment newInstance () {
        return new HomeFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        receiver = new BaseReceiver<>(localCallback);
        LocalBroadcastManager.getInstance(getContext().getApplicationContext()).registerReceiver(receiver,
                new IntentFilter(SendLocalBroadcast.KEY_UPDATE_MUSIC_TITLE_VIEW));
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mBinding.setHomeViewModel(mViewModel);
        initViews(mBinding.getRoot());
        bindViews();

    }

    private MusicTitleView musicTitleView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initViews (View view) {
        super.initViews(view);
        musicTitleView = view.findViewById(R.id.home_music_title_view);
        musicTitleView.setActivity((MusicActivity) getActivity());
    }

    private LocalCallback<Integer> localCallback = new LocalCallback<Integer>() {
        @Override
        public void onChangeData (Integer data) {
            Log.d("MusicTitleView", "HomeFragment onChangeData: data = " + data);
            musicTitleView.updateView();
        }
    };

    @Override
    public void onResume () {
        super.onResume();
        if (musicTitleView != null) {
            musicTitleView.updateView();
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        if (getContext() == null) {
            return;
        }
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void bindViews () {
        super.bindViews();
        final List<Fragment> mFragmentList = new ArrayList<>();
        MusicFragment musicFragment = MusicFragment.newInstance();
        musicFragment.setArguments(getArguments());
        mFragmentList.add(musicFragment);
        mFragmentList.add(NewsFragment.newInstance());
        mFragmentList.add(NewsFragment.newInstance());
        mFragmentList.add(NewsFragment.newInstance());
        setCustomTabLayout();

//        final IMusicAidlInterface service = (IMusicAidlInterface) getArguments().getBinder(Key.IBINDER);
//        mViewModel.initDatabaseData(new LocalCallback<String>() {
//            @Override
//            public void onChangeData(String data) {
//                try {
//                    if (service != null)
//                        service.initPlayList();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        mBinding.baseViewPager.setAdapter(new BaseFragmentPageAdapter(getChildFragmentManager(), mFragmentList, titles));

        mBinding.tabLayout.addOnTabSelectedListener(new TabLayoutCallback() {
            @Override
            public void onTabSelected (TabLayout.Tab tab) {
                super.onTabSelected(tab);
                Log.d(TAG, "onTabSelected: tab.position = " + tab.getPosition());
                mBinding.baseViewPager.setCurrentItem(tab.getPosition());
            }
        });
        mBinding.baseViewPager.addOnPageChangeListener(new OnPageChangerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageSelected (int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected: position = " + position);
                Objects.requireNonNull(mBinding.tabLayout.getTabAt(position)).select();
            }
        });
    }

    /**
     * 自定义布局TabLayout
     */
    private void setCustomTabLayout () {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = mBinding.tabLayout.newTab();
            tab.setCustomView(getTabView(i));
            mBinding.tabLayout.addTab(tab);
        }
    }

    /**
     * 获取对应tab view
     *
     * @param index 下标
     * @return View
     */
    private View getTabView (int index) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tablayout_home, (ViewGroup) mBinding.getRoot(), false);
        ImageView imageView = view.findViewById(R.id.tab_image);
        imageView.setImageResource(images[index]);
        TextView textView = view.findViewById(R.id.tab_content);
        textView.setText(titles[index]);
        return view;


    }
}
