package com.dht.music.ui.recentplay;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dht.baselib.base.BaseAndroidViewModel;
import com.dht.baselib.callback.LocalCallback;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.bean.music.RecentPlayBean;
import com.dht.music.repository.RecentPlayRepository;

import java.util.List;

/**
 * @author Administrator
 */
public class RecentPlayViewModel extends BaseAndroidViewModel {


    private static final String TAG = "RecentPlayViewModel";

    private MutableLiveData<List<RecentPlayBean>> musicData = new MutableLiveData<>();

    private RecentPlayRepository recentPlayRepository;

    public RecentPlayViewModel(@NonNull Application application) {
        super(application);
        recentPlayRepository = new RecentPlayRepository(application);
    }

    /**
     * 获取最近播放实体类
     *
     * @return musicData
     */
    public MutableLiveData<List<RecentPlayBean>> getRecentPlayEntityData() {
        recentPlayRepository.getRecentPlayEntities(new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData(List<RecentPlayBean> data) {
                musicData.postValue(data);
            }
        });

        return musicData;
    }

    /**
     * 插入或更新最近RecentPlayEntity实体类
     */
    public void insertOrUpdate(MusicBean bean) {
        recentPlayRepository.insertOrUpdate(bean);
    }

    /**
     * 按所有时间升序或降序排列
     */
    public void getAscRecentAllTime() {
        recentPlayRepository.getAscRecentAllTime(new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData(List<RecentPlayBean> data) {
                super.onChangeData(data);
                musicData.postValue(data);
            }
        });

    }

    /**
     * 按最近播放时间升序或降序排列
     */
    public void getAscRecentPlayTime() {
        recentPlayRepository.getAscRecentPlayTime(new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData(List<RecentPlayBean> data) {
                super.onChangeData(data);
                musicData.postValue(data);
            }
        });

    }

    /**
     * 按最近一周播放次数升序或降序排列
     */
    public void getAscRecentOneWeek() {
        recentPlayRepository.getAscRecentOneWeek(new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData(List<RecentPlayBean> data) {
                super.onChangeData(data);
                musicData.postValue(data);
            }
        });
    }

    /**
     * 根据歌曲名称删除对应数据
     *
     * @param songName    歌曲名称
     * @param dynamicType 选择类型
     */
    public void deleteCurrentRecentEntity(String songName, final RecentPlayAdapter.DynamicType dynamicType) {
        recentPlayRepository.deleteRecentPlayEntity(songName, new LocalCallback<String>() {
            @Override
            public void onChangeData() {
                super.onChangeData();
                switch (dynamicType) {
                    case PLAY_TIME:
                        getAscRecentPlayTime();
                        break;
                    case PLAY_COUNT:
                        getAscRecentOneWeek();
                        break;
                    case PLAY_TOTAL:
                        getAscRecentAllTime();
                        break;
                    default:
                }
            }
        });
    }


}
