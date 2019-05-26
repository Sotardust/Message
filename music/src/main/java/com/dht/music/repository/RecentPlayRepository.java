package com.dht.music.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.ObservableCallback;
import com.dht.baselib.util.LogUtil;
import com.dht.baselib.util.ObservableUtil;
import com.dht.databaselib.BaseDatabase;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.bean.music.RecentPlayBean;
import com.dht.databaselib.dao.RecentPlayDao;
import com.dht.databaselib.preferences.MessagePreferences;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * created by dht on 2019/1/17 15:17
 *
 * @author Administrator
 */
public class RecentPlayRepository {

    private static final String TAG = "RecentPlayRepository";

    private RecentPlayDao recentPlayDao;

    public RecentPlayRepository (Application application) {
        BaseDatabase appDatabase = BaseDatabase.getInstance(application.getApplicationContext());
        recentPlayDao = appDatabase.getRecentPlayDao();
        Log.d(TAG, "MusicRepository: musicDao = " + recentPlayDao);
    }

    /**
     * 插入或更新最近RecentPlayEntity实体类
     *
     * @param music Music实体
     */
    public void insertOrUpdate (final MusicBean music) {

        Observable.create(new ObservableCallback<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                final long personId = MessagePreferences.getInstance().getPersonId();
                List<RecentPlayBean> entities = new ArrayList<>(recentPlayDao.getPersonRecentPlayEntity(personId));

                ArrayList<String> names = new ArrayList<>();
                for (RecentPlayBean entity : entities) {
                    names.add(entity.songName);
                }
                //不包含则像数据库中插入数据
                if (!names.contains(music.name)) {
                    RecentPlayBean entity = new RecentPlayBean();
                    entity.music = music;
                    entity.songName = music.name;
                    entity.personId = personId;
                    entity.playCount = 1;
                    entity.playTotal = 1;
                    entity.playTime = System.currentTimeMillis();
                    entity.music = music;
                    recentPlayDao.addRecentPlayEntity(entity);
                    emitter.onNext("insert " + ObservableUtil.KEY_SUCCESSFUL);
                    return;
                }
                int index = names.lastIndexOf(music.name);
                final RecentPlayBean entity = entities.get(index);
                int playCount = 0;
                long currentTime = System.currentTimeMillis();
                //大于1周
                if (currentTime - entity.playTime >= 7 * 24 * 3600L) {
                    playCount = 1;
                } else {
                    ++playCount;
                }
                recentPlayDao.updateRecentPlayEntity(entity.id, currentTime, playCount, entity.playTotal + 1);
                emitter.onNext("update " + ObservableUtil.KEY_SUCCESSFUL);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ObservableUtil.subscriber(new LocalCallback<String>() {
                    @Override
                    public void onChangeData (String data) {
                        if (TextUtils.isEmpty(data)) {
                            LogUtil.writeInfo(TAG, "insertRecentPlayMusic", "异常");
                        }
                        Log.d(TAG, "onChangeData: data" + data);
                    }
                }));

    }


    /**
     * 从数据库中查找所有对应最近播放歌曲数据
     *
     * @param musicLocalCallback LocalCallback
     */
    public void getRecentPlayEntities (final LocalCallback<List<RecentPlayBean>> musicLocalCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayBean>>() {
            @Override
            public void subscribe (ObservableEmitter<List<RecentPlayBean>> emitter) throws Exception {
                super.subscribe(emitter);
                List<RecentPlayBean> entities = new ArrayList<>(
                        recentPlayDao.getPersonRecentPlayEntity(MessagePreferences.getInstance().getPersonId()));
                emitter.onNext(entities);

            }
        }, new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData (List<RecentPlayBean> data) {
                super.onChangeData(data);
                musicLocalCallback.onChangeData(data == null ? new ArrayList<RecentPlayBean>() : data);
            }
        });
    }

    /**
     * 获取最近播放歌曲总个数
     *
     * @param localCallback 回调接口
     */
    public void getPlayTotal (final LocalCallback<Integer> localCallback) {
        ObservableUtil.execute(new ObservableCallback<Integer>() {
            @Override
            public void subscribe (ObservableEmitter<Integer> emitter) throws Exception {
                super.subscribe(emitter);
                int total = recentPlayDao.getRecentPlayTotal(MessagePreferences.getInstance().getPersonId());
                emitter.onNext(total);
            }
        }, new LocalCallback<Integer>() {
            @Override
            public void onChangeData (Integer data) {
                localCallback.onChangeData(data == null ? 0 : data);
            }
        });
    }

    /**
     * 获取所有时间升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAscRecentAllTime (final LocalCallback<List<RecentPlayBean>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayBean>>() {
            @Override
            public void subscribe (ObservableEmitter<List<RecentPlayBean>> emitter) throws Exception {
                super.subscribe(emitter);

                List<RecentPlayBean> entities = recentPlayDao.getAscRecentAllTime(MessagePreferences.getInstance().getPersonId());
                emitter.onNext(entities);
            }
        }, new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData (List<RecentPlayBean> data) {
                super.onChangeData(data);
                Log.d(TAG, "onChangeData: ");
                localCallback.onChangeData(data == null ? new ArrayList<RecentPlayBean>() : data);
            }
        });
    }

    /**
     * 获取最近播放升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAscRecentPlayTime (final LocalCallback<List<RecentPlayBean>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayBean>>() {
            @Override
            public void subscribe (ObservableEmitter<List<RecentPlayBean>> emitter) throws Exception {
                super.subscribe(emitter);

                List<RecentPlayBean> entities = recentPlayDao.getAscRecentPlayTime(MessagePreferences.getInstance().getPersonId());
                emitter.onNext(entities);
            }
        }, new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData (List<RecentPlayBean> data) {
                super.onChangeData(data);
                localCallback.onChangeData(data == null ? new ArrayList<RecentPlayBean>() : data);
            }
        });
    }


    /**
     * 获取最近一周升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAscRecentOneWeek (final LocalCallback<List<RecentPlayBean>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayBean>>() {
            @Override
            public void subscribe (ObservableEmitter<List<RecentPlayBean>> emitter) throws Exception {
                super.subscribe(emitter);
                List<RecentPlayBean> entities = recentPlayDao.getAscRecentOneWeek(MessagePreferences.getInstance().getPersonId(), System.currentTimeMillis());
                emitter.onNext(entities);
            }
        }, new LocalCallback<List<RecentPlayBean>>() {
            @Override
            public void onChangeData (List<RecentPlayBean> data) {
                super.onChangeData(data);
                localCallback.onChangeData(data == null ? new ArrayList<RecentPlayBean>() : data);
            }
        });
    }

    /**
     * 根据音乐名称删除播放记录
     *
     * @param songName      歌曲名称
     * @param localCallback 回调接口
     */
    public void deleteRecentPlayEntity (final String songName, final LocalCallback<String> localCallback) {
        ObservableUtil.execute(new ObservableCallback<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                recentPlayDao.deleteRecentPlayEntity(MessagePreferences.getInstance().getPersonId(), songName);
                emitter.onNext(ObservableUtil.KEY_SUCCESSFUL);
            }
        }, new LocalCallback<String>() {
            @Override
            public void onChangeData (String data) {
                super.onChangeData(data);
                localCallback.onChangeData();
            }
        });
    }


}
