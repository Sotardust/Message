package com.dai.message.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.repository.dao.RecentPlayDao;
import com.dai.message.repository.entity.RecentPlayEntity;
import com.dai.message.repository.preferences.Config;
import com.dai.message.util.LogUtil;
import com.dai.message.util.rxjava.ObservableUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * created by dht on 2019/1/17 15:17
 */
public class RecentPlayRepository {

    private static final String TAG = "RecentPlayRepository";

    private RecentPlayDao recentPlayDao;

    public RecentPlayRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        recentPlayDao = appDatabase.getRecentPlayDao();
        Log.d(TAG, "MusicRepository: musicDao = " + recentPlayDao);
    }

    /**
     * 插入或更新最近RecentPlayEntity实体类
     *
     * @param music Music实体
     */
    public void insertOrUpdate(final Music music) {

        Observable.create(new ObservableCallback<List<RecentPlayEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RecentPlayEntity>> emitter) throws Exception {
                super.subscribe(emitter);
                List<RecentPlayEntity> list = new ArrayList<>(recentPlayDao.getPersonRecentPlayEntity(Config.getInstance().getPersonId()));
                emitter.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<RecentPlayEntity>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final List<RecentPlayEntity> entities) throws Exception {
                        ArrayList<String> names = new ArrayList<>();
                        for (RecentPlayEntity entity : entities) {
                            names.add(entity.songName);
                        }
                        if (!names.contains(music.name)) //不包含则像数据库中插入数据
                            return Observable.create(new ObservableCallback<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    super.subscribe(emitter);
                                    RecentPlayEntity entity = new RecentPlayEntity();
                                    entity.music = music;
                                    entity.songName = music.name;
                                    entity.personId = Config.getInstance().getPersonId();
                                    entity.playCount = 1;
                                    entity.playTotal = 1;
                                    entity.playTime = System.currentTimeMillis();
                                    entity.music = music;
                                    recentPlayDao.addRecentPlayEntity(entity);
                                    emitter.onNext("insert " + ObservableUtil.KEY_SUCCESSFUL);
                                }
                            });

                        int index = names.lastIndexOf(music.name);
                        final RecentPlayEntity entity = entities.get(index);
                        return Observable.create(new ObservableCallback<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                super.subscribe(emitter);
                                int playCount = 0;
                                long currentTime = System.currentTimeMillis();
                                if (currentTime - entity.playTime >= 7 * 24 * 3600L) { //大于1周
                                    playCount = 1;
                                } else {
                                    ++playCount;
                                }
                                recentPlayDao.updateRecentPlayEntity(entity.id, currentTime, playCount, entity.playTotal + 1);
                                emitter.onNext("update " + ObservableUtil.KEY_SUCCESSFUL);
                            }
                        });


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ObservableUtil.subscriber(new LocalCallback<String>() {
                    @Override
                    public void onChangeData(String data) {
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
    public void getRecentPlayEntities(final LocalCallback<List<RecentPlayEntity>> musicLocalCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RecentPlayEntity>> emitter) throws Exception {
                super.subscribe(emitter);
                List<RecentPlayEntity> entities = new ArrayList<>(
                        recentPlayDao.getPersonRecentPlayEntity(Config.getInstance().getPersonId()));
                emitter.onNext(entities);

            }
        }, new LocalCallback<List<RecentPlayEntity>>() {
            @Override
            public void onChangeData(List<RecentPlayEntity> data) {
                super.onChangeData(data);
                musicLocalCallback.onChangeData(data == null ? new ArrayList<RecentPlayEntity>() : data);
            }
        });
    }

    /**
     * 获取最近播放歌曲总个数
     *
     * @param localCallback 回调接口
     */
    public void getPlayTotal(final LocalCallback<Integer> localCallback) {
        ObservableUtil.execute(new ObservableCallback<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                super.subscribe(emitter);
                int total = recentPlayDao.getRecentPlayTotal(Config.getInstance().getPersonId());
                emitter.onNext(total);
            }
        }, new LocalCallback<Integer>() {
            @Override
            public void onChangeData(Integer data) {
                localCallback.onChangeData(data == null ? 0 : data);
            }
        });
    }

    /**
     * 获取所有时间升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAscRecentAllTime(final LocalCallback<List<RecentPlayEntity>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RecentPlayEntity>> emitter) throws Exception {
                super.subscribe(emitter);

                List<RecentPlayEntity> entities = recentPlayDao.getAscRecentAllTime(Config.getInstance().getPersonId());
                emitter.onNext(entities);
            }
        }, new LocalCallback<List<RecentPlayEntity>>() {
            @Override
            public void onChangeData(List<RecentPlayEntity> data) {
                super.onChangeData(data);
                Log.d(TAG, "onChangeData: ");
                localCallback.onChangeData(data == null ? new ArrayList<RecentPlayEntity>() : data);
            }
        });
    }

    /**
     * 获取最近播放升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAscRecentPlayTime(final LocalCallback<List<RecentPlayEntity>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RecentPlayEntity>> emitter) throws Exception {
                super.subscribe(emitter);

                List<RecentPlayEntity> entities = recentPlayDao.getAscRecentPlayTime(Config.getInstance().getPersonId());
                emitter.onNext(entities);
            }
        }, new LocalCallback<List<RecentPlayEntity>>() {
            @Override
            public void onChangeData(List<RecentPlayEntity> data) {
                super.onChangeData(data);
                localCallback.onChangeData(data == null ? new ArrayList<RecentPlayEntity>() : data);
            }
        });
    }


    /**
     * 获取最近一周升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAscRecentOneWeek(final LocalCallback<List<RecentPlayEntity>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<List<RecentPlayEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RecentPlayEntity>> emitter) throws Exception {
                super.subscribe(emitter);
                List<RecentPlayEntity> entities = recentPlayDao.getAscRecentOneWeek(Config.getInstance().getPersonId(), System.currentTimeMillis());
                emitter.onNext(entities);
            }
        }, new LocalCallback<List<RecentPlayEntity>>() {
            @Override
            public void onChangeData(List<RecentPlayEntity> data) {
                super.onChangeData(data);
                localCallback.onChangeData(data == null ? new ArrayList<RecentPlayEntity>() : data);
            }
        });
    }

    /**
     * 根据音乐名称删除播放记录
     *
     * @param songName      歌曲名称
     * @param localCallback 回调接口
     */
    public void deleteRecentPlayEntity(final String songName, final LocalCallback<String> localCallback) {
        ObservableUtil.execute(new ObservableCallback<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                recentPlayDao.deleteRecentPlayEntity(Config.getInstance().getPersonId(), songName);
                emitter.onNext(ObservableUtil.KEY_SUCCESSFUL);
            }
        }, new LocalCallback<String>() {
            @Override
            public void onChangeData(String data) {
                super.onChangeData(data);
                localCallback.onSuccessful();
            }
        });
    }


}
