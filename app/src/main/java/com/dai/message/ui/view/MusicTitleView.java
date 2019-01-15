package com.dai.message.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dai.message.R;
import com.dai.message.bean.IMusicAidlInterface;
import com.dai.message.bean.Music;
import com.dai.message.util.Key;
import com.dai.message.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐播放栏
 * <p>
 * created by dht on 2019/1/15 11:08
 */
public class MusicTitleView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "MusicTitleView";

    private ImageView back;
    private ImageView avatar;
    private TextView songName;
    private TextView author;
    private TextView play;
    private TextView playList;

    private IMusicAidlInterface musicService;
    private Music currentMusic;
    private List<Music> list = new ArrayList<>();
    private Context context;

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
        avatar = view.findViewById(R.id.music_title_avatar);
        songName = view.findViewById(R.id.music_title_song_name);
        author = view.findViewById(R.id.music_title_author);
        play = view.findViewById(R.id.music_title_play);
        playList = view.findViewById(R.id.music_title_play_list);

        back.setOnClickListener(this);
        avatar.setOnClickListener(this);
        songName.setOnClickListener(this);
        author.setOnClickListener(this);
        play.setOnClickListener(this);
        playList.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setBundleData(Bundle bundle) {
        try {
            if (bundle != null) {
                musicService = (IMusicAidlInterface) bundle.getBinder(Key.IBINDER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setBundleData: e", e);
        }
    }

    private Activity activity;

    /**
     * 设置对应的Activity
     *
     * @param activity Activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    /**
     * 更新视图View
     *
     * @param isInit 若是第一次初始化则为 paly =播放
     */
    public void updateView(final boolean isInit) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    currentMusic = musicService.getCurrentMusic();
                    Log.d(TAG, "*********************run: currentMusic = " + currentMusic);
                    songName.setText(currentMusic.name);
                    author.setText(currentMusic.author);
                    play.setText(context.getString(isInit ? R.string.play : musicService.isPlaying() ? R.string.play : R.string.pause));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.music_title_back:
                    activity.finish();
                    break;
                case R.id.music_title_play:
                    if (musicService.getCurrentPosition() == 0) {
                        musicService.playCurrentMusic();
                    } else if (musicService.isPlaying()) {
                        musicService.pause();
                    } else {
                        musicService.playPause();
                    }
                    updateView(false);
                    break;
                case R.id.music_title_play_list:
                    ToastUtil.toastCustom(context, "播放列表", 500);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "onClick: e", e);
        }
    }
}
