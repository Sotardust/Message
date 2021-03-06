package com.dht.music.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dht.baselib.base.BaseActivity;
import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.music.MusicModel;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.music.R;
import com.dht.music.dialog.PlayListDialogFragment;
import com.dht.music.repository.RecentPlayRepository;

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
    private RecentPlayRepository repository ;

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


    private BaseActivity activity;
    private MusicModel mModel;

    /**
     * 设置对应的Activity
     *
     * @param activity Activity
     */
    public void setActivity(BaseActivity activity, MusicModel mModel) {
        this.activity = activity;
        this.mModel = mModel;
        repository = new RecentPlayRepository(activity.getApplication());
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
        Log.d(TAG, "updateView: ");
        boolean isPlaying = mModel.isPlaying();
        String value = context.getString(isPlaying ? R.string.playing : R.string.pause);
        play.setText(value);
        currentMusic = mModel.getCurrentMusic();
        if (currentMusic == null) {
            return;
        }
        repository.insertOrUpdate(currentMusic);
        songName.setText(currentMusic.name);
        author.setText(currentMusic.author);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.music_title_back) {
            activity.finish();
            return;
        }
        if (i == R.id.music_title_play) {


            if (mModel.isPlaying()) {
                mModel.pause();
            } else {
                mModel.playPause();
            }
            updateView();
            return;
        }
        if (i == R.id.music_title_play_list) {
            final PlayListDialogFragment playListDialogFragment = PlayListDialogFragment.newInstance();
            playListDialogFragment.show(activity, new LocalCallback<MusicBean>() {
                @Override
                public void onChangeData(MusicBean data) {
                    super.onChangeData(data);
                    mModel.playMusic(data);
                    playListDialogFragment.dismiss();
                }
            });
            return;
        }
        if (i == R.id.music_relative) {

            if (MessagePreferences.INSTANCE.getCurrentMusic() == null) {
                ToastUtil.toastCustom(context, R.string.no_play_music, 500);
                return;
            }
//                    Bundle bundle = new Bundle();
//                    bundle.putBinder(Key.IBINDER, (IBinder) activity);
//                    Intent intent = new Intent(context, PlayMusicActivity.class);
//                    intent.putExtra(Key.IBINDER, bundle);
//                    intent.putExtra(Key.MUSIC, MessagePreferences.INSTANCE.getCurrentMusic());
//                    context.startActivity(intent);
        }
    }
}
