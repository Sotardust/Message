package com.dai.message.repository;

import android.app.Application;
import android.util.Log;

import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.callback.ObserverCallback;
import com.dai.message.repository.dao.MusicDao;
import com.dai.message.util.rxjava.RxJavaObservable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;

/**
 * created by dht on 2019/1/14 10:18
 */
public class MusicRepository {

    private static final String TAG = "MusicRepository";

    private MusicDao musicDao;

    public MusicRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        musicDao = appDatabase.getMusicDao();
        Log.d(TAG, "MusicRepository: musicDao = " + musicDao);
    }

    /**
     * 向数据表中插入数据
     *
     * @param musics Music集合
     */
    public void insertMusic(final ArrayList<Music> musics) {
        getAllNames(new LocalCallback<List<String>>() {
            @Override
            public void onChangeData(List<String> data) {
                final List<String> names = new ArrayList<>(data);
                RxJavaObservable.getInstance()
                        .execute(new ObservableCallback<String>() {
                            @Override
                            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                                super.subscribe(emitter);
                                for (Music music : musics) {
                                    if (!names.contains(music.name))
                                        musicDao.addMusic(music);
                                }
                                emitter.onNext(RxJavaObservable.KEY_SUCCESSFUL);
                            }
                        }, new ObserverCallback<String>() {
                            @Override
                            public void onNext(String s) {
                                super.onNext(s);
                                Log.d(TAG, "insertMusic onNext: s = " + s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Log.e(TAG, "insertMusic onError: e", e);
                            }
                        });

            }
        });

    }

    /**
     * 删除本地音乐文件
     *
     * @param name 歌曲名称
     */
    public void deleteMusic(final String name) {
        RxJavaObservable.getInstance()
                .execute(new ObservableCallback<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        super.subscribe(emitter);
                        musicDao.deleteMusic(name);
                        emitter.onNext(RxJavaObservable.KEY_SUCCESSFUL);
                    }
                }, new ObserverCallback<String>() {
                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        Log.d(TAG, "deleteMusic onNext: s = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "deleteMusic onError: e", e);
                    }
                });
    }

    /**
     * 获取所有本地音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAllMusics(final LocalCallback<ArrayList<Music>> localCallback) {
        RxJavaObservable.getInstance()
                .execute(new ObservableCallback<ArrayList<Music>>() {
                    @Override
                    public void subscribe(ObservableEmitter<ArrayList<Music>> emitter) throws Exception {
                        super.subscribe(emitter);
                        ArrayList<Music> musicList = new ArrayList<>(musicDao.getAllMusics());
                        emitter.onNext(musicList);
                    }
                }, new ObserverCallback<ArrayList<Music>>() {
                    @Override
                    public void onNext(ArrayList<Music> music) {
                        super.onNext(music);
                        localCallback.onChangeData(music);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        localCallback.onChangeData(new ArrayList<Music>());
                        Log.e(TAG, "getAllMusics onError: e", e);

                    }
                });
    }

    /**
     * 获取音乐总个数
     *
     * @param localCallback 回调接口
     */
    public void getMusicTotal(final LocalCallback<Integer> localCallback) {
        RxJavaObservable.getInstance()
                .execute(new ObservableCallback<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        super.subscribe(emitter);
                        int total = musicDao.getMusicTotal();
                        emitter.onNext(total);
                    }
                }, new ObserverCallback<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        localCallback.onChangeData(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        localCallback.onChangeData(0);
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    /**
     * 获取数据库中所有歌曲名称
     *
     * @param callback 回调接口
     */
    private void getAllNames(final LocalCallback<List<String>> callback) {
        RxJavaObservable.getInstance()
                .execute(new ObservableCallback<List<String>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                        super.subscribe(emitter);
                        List<String> list = new ArrayList<>(musicDao.getAllNames());
                        emitter.onNext(list);
                    }
                }, new ObserverCallback<List<String>>() {
                    @Override
                    public void onNext(List<String> list) {
                        super.onNext(list);
                        callback.onChangeData(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callback.onChangeData(new ArrayList<String>());
                        Log.e(TAG, "getAllNames onError: ", e);
                    }
                });
    }

}
