package com.dai.message.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dai.message.R;
import com.dai.message.bean.IMusicAidlInterface;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.callback.ObservableCallback;
import com.dai.message.repository.preferences.Config;
import com.dai.message.ui.dialog.PlayListDialogFragment;
import com.dai.message.ui.music.playmusic.PlayMusicActivity;
import com.dai.message.util.Key;
import com.dai.message.util.ToastUtil;
import com.dai.message.util.rxjava.ObservableUtil;
import com.dht.commonlib.base.BaseActivity;

import io.reactivex.ObservableEmitter;

/**
 * 音乐播放栏
 * <p>
 * created by dht on 2019/1/15 11:08
 */
public class MusicTitleView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "MusicTitleView";

    private ImageView back;
    private RelativeLayout musicRelative;
    private ImageView avatar;
    private TextView songName;
    private TextView author;
    private TextView play;
    private TextView playList;

    private IMusicAidlInterface musicService;
    private Music currentMusic;
    private Context context;
    private Bundle bundle;

    public MusicTitleView(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public MusicTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public MusicTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    private void initView() {
        View view = inflate(context, R.layout.view_title_music, this);
        back = view.findViewById(R.id.music_title_back);

        musicRelative = view.findViewById(R.id.music_relative);
        avatar = view.findViewById(R.id.music_title_avatar);
        songName = view.findViewById(R.id.music_title_song_name);
        author = view.findViewById(R.id.music_title_author);
        play = view.findViewById(R.id.music_title_play);
        playList = view.findViewById(R.id.music_title_play_list);

        back.setOnClickListener(this);
        musicRelative.setOnClickListener(this);
        play.setOnClickListener(this);
        playList.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setBundleData(Bundle bundle) {
        try {
            this.bundle = bundle;
            if (bundle != null) {
                musicService = (IMusicAidlInterface) bundle.getBinder(Key.IBINDER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setBundleData: e", e);
        }
    }

    private BaseActivity activity;

    /**
     * 设置对应的Activity
     *
     * @param activity Activity
     */
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * 设置返回按钮可见
     */
    public void setBackViewVisibility() {
        back.setVisibility(VISIBLE);
    }

    /**
     * 更新视图View
     */
    public void updateView() {
        ObservableUtil.execute(new ObservableCallback<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                boolean isPlaying = musicService.isPlaying();
                if (Config.getInstance().isFirstPlay()) {
                    emitter.onNext(context.getString(R.string.play));
                } else {
                    String value = context.getString(!isPlaying ? R.string.pause : R.string.playing);
                    emitter.onNext(value);
                }
            }
        }, new LocalCallback<String>() {
            @Override
            public void onChangeData(String data) {
                super.onChangeData(data);
                try {
                    currentMusic = musicService.getCurrentMusic();
                    if (currentMusic == null) return;
                    songName.setText(currentMusic.name);
                    author.setText(currentMusic.author);
                    play.setText(data);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.music_title_back:
                    activity.finish();
                    break;
                case R.id.music_title_play:
                    if (Config.getInstance().isFirstPlay()) {
                        musicService.playCurrentMusic();
                        Config.getInstance().setIsFirstPlay(false);
                    } else if (musicService.isPlaying()) {
                        musicService.pause();
                    } else {
                        musicService.playPause();
                    }
                    updateView();
                    break;
                case R.id.music_title_play_list:
                    PlayListDialogFragment playListDialogFragment = PlayListDialogFragment.newInstance();
                    playListDialogFragment.setArguments(bundle);
                    playListDialogFragment.show(activity);
                    break;
                case R.id.music_relative:
                    Config.getInstance().setIsFirstPlay(false);
                    Log.d(TAG, "onClick: music_relative = ");
                    if (Config.getInstance().getCurrentMusic() == null) {
                        ToastUtil.toastCustom(context, R.string.no_play_music, 500);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBinder(Key.IBINDER, (IBinder) musicService);
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra(Key.IBINDER, bundle);
                    intent.putExtra(Key.MUSIC, Config.getInstance().getCurrentMusic());
                    context.startActivity(intent);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "onClick: e", e);
        }
    }
}
