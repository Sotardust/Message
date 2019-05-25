package com.dht.music.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.ObservableCallback;
import com.dht.baselib.util.ObservableUtil;
import com.dht.databaselib.BaseDatabase;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.dao.MusicDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * created by dht on 2019/1/14 10:18
 */
public class MusicRepository {

    private static final String TAG = "MusicRepository";

    private MusicDao musicDao;

    public MusicRepository (Application application) {
        BaseDatabase appDatabase = BaseDatabase.getInstance(application.getApplicationContext());
        musicDao = appDatabase.getMusicDao();
        Log.d(TAG, "MusicRepository: musicDao = " + musicDao);
    }

    /**
     * 先获取数据库所有歌曲名 查看是否已经存在若不存在则向数据表中插入数据
     *
     * @param musics Music集合
     */
    @SuppressLint("CheckResult")
    public void insertMusic (final ArrayList<MusicBean> musics, final LocalCallback<String> localCallback) {
        Observable.create(new ObservableCallback<List<String>>() {
            @Override
            public void subscribe (ObservableEmitter<List<String>> emitter) throws Exception {
                super.subscribe(emitter);
                List<String> list = new ArrayList<>(musicDao.getAllNames());
                emitter.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept (List<String> strings) throws Exception {
                        Log.d(TAG, "accept: strings = " + strings);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<String>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply (final List<String> names) throws Exception {

                        return Observable.create(new ObservableCallback<String>() {
                            @Override
                            public void subscribe (ObservableEmitter<String> emitter) throws Exception {
                                super.subscribe(emitter);
                                for (MusicBean music : musics) {
                                    if (!names.contains(music.name)) {
                                        musicDao.addMusic(music);
                                    }
                                }
                                emitter.onNext(ObservableUtil.KEY_SUCCESSFUL);
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ObservableUtil.subscriber(new LocalCallback<String>() {
                    @Override
                    public void onChangeData (String data) {
                        Log.d(TAG, "onChangeData: data" + data);
                        localCallback.onChangeData();
                    }
                }));
    }

    /**
     * 删除本地音乐文件
     *
     * @param name 歌曲名称
     */
    public void deleteMusic (final String name) {
        ObservableUtil.execute(new ObservableCallback<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                musicDao.deleteMusic(name);
                emitter.onNext(ObservableUtil.KEY_SUCCESSFUL);
            }
        }, new LocalCallback<String>() {
            @Override
            public void onChangeData (String data) {
                Log.d(TAG, "deleteMusic onNext: s = " + data);
            }
        });
    }

    /**
     * 获取所有本地音乐数据
     *
     * @param localCallback 回调接口
     */
    public void getAllMusics (final LocalCallback<ArrayList<MusicBean>> localCallback) {
        ObservableUtil.execute(new ObservableCallback<ArrayList<MusicBean>>() {
            @Override
            public void subscribe (ObservableEmitter<ArrayList<MusicBean>> emitter) throws Exception {
                super.subscribe(emitter);
                ArrayList<MusicBean> musicList = new ArrayList<>(musicDao.getAllMusics());
                emitter.onNext(musicList);
            }
        }, new LocalCallback<ArrayList<MusicBean>>() {
            @Override
            public void onChangeData (ArrayList<MusicBean> musics) {
                localCallback.onChangeData(musics == null ? new ArrayList<MusicBean>() : musics);
            }
        });
    }

    /**
     * 获取音乐总个数
     *
     * @param localCallback 回调接口
     */
    public void getMusicTotal (final LocalCallback<Integer> localCallback) {
        ObservableUtil.execute(new ObservableCallback<Integer>() {
            @Override
            public void subscribe (ObservableEmitter<Integer> emitter) throws Exception {
                super.subscribe(emitter);
                int total = musicDao.getMusicTotal();
                emitter.onNext(total);
            }
        }, new LocalCallback<Integer>() {
            @Override
            public void onChangeData (Integer data) {
                localCallback.onChangeData(data == null ? 0 : data);
            }
        });
    }
}
