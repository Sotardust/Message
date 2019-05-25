package com.dht.music.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.ObservableCallback;
import com.dht.baselib.util.ObservableUtil;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.music.MusicActivity;
import com.dht.music.R;
import com.dht.music.dialog.PlayListDialogFragment;

import io.reactivex.ObservableEmitter;

/**
 * 音乐播放栏
 * <p>
 * created by dht on 2019/1/15 11:08
 *
 * @author Administrator
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

    private MusicBean currentMusic;
    private Context context;
    private Bundle bundle;

    public MusicTitleView (Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public MusicTitleView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public MusicTitleView (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    private void initView () {
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


    private MusicActivity activity;

    /**
     * 设置对应的Activity
     *
     * @param activity Activity
     */
    public void setActivity (MusicActivity activity) {
        this.activity = activity;
    }

    /**
     * 设置返回按钮可见
     */
    public void setBackViewVisibility () {
        back.setVisibility(VISIBLE);
    }

    /**
     * 更新视图View
     */
    public void updateView () {
        ObservableUtil.execute(new ObservableCallback<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> emitter) throws Exception {
                super.subscribe(emitter);
                boolean isPlaying = activity.isPlaying();
//                if (Config.getInstance().isFirstPlay()) {
//                    emitter.onNext(context.getString(R.string.play));
//                } else {
                String value = context.getString(!isPlaying ? R.string.pause : R.string.playing);
                emitter.onNext(value);
//                }
            }
        }, new LocalCallback<String>() {
            @Override
            public void onChangeData (String data) {
                super.onChangeData(data);
                currentMusic = activity.getCurrentMusic();
                if (currentMusic == null) {
                    return;
                }
                songName.setText(currentMusic.name);
                author.setText(currentMusic.author);
                play.setText(data);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick (View v) {
        int i = v.getId();
        if (i == R.id.music_title_back) {
            activity.finish();
        } else if (i == R.id.music_title_play) {
            if (MessagePreferences.getInstance().isFirstPlay()) {
                activity.playCurrentMusic();
                MessagePreferences.getInstance().setIsFirstPlay(false);
            } else if (activity.isPlaying()) {
                activity.pause();
            } else {
                activity.playPause();
            }
            updateView();
        } else if (i == R.id.music_title_play_list) {
            PlayListDialogFragment playListDialogFragment = PlayListDialogFragment.newInstance();
            playListDialogFragment.setArguments(bundle);
            playListDialogFragment.show(activity);
        } else if (i == R.id.music_relative) {
            MessagePreferences.getInstance().setIsFirstPlay(false);
//                    Log.d(TAG, "onClick: music_relative = ");
            if (MessagePreferences.getInstance().getCurrentMusic() == null) {
                ToastUtil.toastCustom(context, R.string.no_play_music, 500);
                return;
            }
//                    Bundle bundle = new Bundle();
//                    bundle.putBinder(Key.IBINDER, (IBinder) activity);
//                    Intent intent = new Intent(context, PlayMusicActivity.class);
//                    intent.putExtra(Key.IBINDER, bundle);
//                    intent.putExtra(Key.MUSIC, MessagePreferences.getInstance().getCurrentMusic());
//                    context.startActivity(intent);
        }
    }
}
