package com.dht.music.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dht.baselib.callback.LocalCallback;
import com.dht.baselib.callback.ObserverCallback;
import com.dht.baselib.util.SimpleTextWatcher;
import com.dht.baselib.util.ToastUtil;
import com.dht.databaselib.bean.music.MusicBean;
import com.dht.databaselib.preferences.MessagePreferences;
import com.dht.eventbus.RxBus;
import com.dht.eventbus.event.UpdatePlayEvent;
import com.dht.music.R;

/**
 * created by dht on 2019/1/15 11:10
 *
 * @author Administrator
 */
public class TopTitleView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "TopTitleView";

    private ImageView back;
    private TextView title;
    private EditText searchEdit;
    private TextView search;
    private ImageView setting;

    private RelativeLayout playRelative;
    private TextView songName;
    private TextView author;
    private ImageView shared;

    private Context context;
    private Activity activity;

    public TopTitleView (Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public TopTitleView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public TopTitleView (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    /**
     * 初始化视图View
     */
    private void initView () {
        View view = inflate(context, R.layout.view_title_top, this);

        back = view.findViewById(R.id.top_title_back);
        title = view.findViewById(R.id.top_title);

        //搜索框
        searchEdit = view.findViewById(R.id.top_edit);
        searchEdit.setVisibility(GONE);
        //搜索按钮
        search = view.findViewById(R.id.top_search);
        //其他按钮
        setting = view.findViewById(R.id.top_setting);
        //播放音乐titleBar显示
        playRelative = view.findViewById(R.id.top_song_info);
        songName = view.findViewById(R.id.top_song_name);
        author = view.findViewById(R.id.top_author);
        //分享按钮
        shared = view.findViewById(R.id.top_shared);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    /**
     * 设置与之关联的activity
     *
     * @param activity Activity
     */
    public void setActivity (Activity activity) {
        this.activity = activity;
    }

    /**
     * 设置文本框监听回调事件
     *
     * @param textWatcher SimpleTextWatcher
     */
    public void setSearchEditTextWatcher (SimpleTextWatcher textWatcher) {
        searchEdit.addTextChangedListener(textWatcher);
    }

    /**
     * 设置分享按钮的回调接口
     *
     * @param localCallback 本地回调接口
     */
    public void setSharedCallback (final LocalCallback<MusicBean> localCallback) {
        shared.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v) {
                localCallback.onChangeData(MessagePreferences.INSTANCE.getCurrentMusic());
            }
        });
    }

    /**
     * 更新视图view 设置title值
     *
     * @param activity Activity
     * @param value    值
     */
    public void updateView (Activity activity, final String value) {
        this.activity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run () {
                title.setVisibility(VISIBLE);
                title.setText(value);
            }
        });
    }

    /**
     * 更新视图View
     *
     */
    public void updatePlayView () {
        RxBus.getInstance().toObservable(UpdatePlayEvent.class)
                .subscribe(new ObserverCallback<UpdatePlayEvent>() {
                    @Override
                    public void onNext (UpdatePlayEvent updatePlayEvent) {
                        super.onNext(updatePlayEvent);
                        final MusicBean music = MessagePreferences.INSTANCE.getCurrentMusic();
                        playRelative.setVisibility(VISIBLE);
                        songName.setText(music.name);
                        author.setText(music.author);
                    }
                });
        RxBus.getInstance().post(new UpdatePlayEvent("updatePlayView"));
    }

    /**
     * 显示本地音乐页title
     */
    public void setLocalTitleBar () {
        title.setVisibility(VISIBLE);
        search.setVisibility(VISIBLE);
        setting.setVisibility(VISIBLE);
    }

    /**
     * 显示最近播放页title
     */
    public void showRecentPlayTitleBar () {
        title.setVisibility(VISIBLE);
    }

    @Override
    public void onClick (View v) {
        int i = v.getId();
        if (i == R.id.top_title_back) {
            activity.finish();
        } else if (i == R.id.top_search) {
            boolean isSearch = context.getString(R.string.search).equals(search.getText().toString());
            title.setVisibility(isSearch ? GONE : VISIBLE);
            if (!isSearch && !TextUtils.isEmpty(searchEdit.getText())) {
                searchEdit.setText(null);
            }
            searchEdit.setVisibility(isSearch ? VISIBLE : GONE);
            search.setText(isSearch ? R.string.close_search : R.string.search);
        } else if (i == R.id.top_setting) {
            ToastUtil.toastCustom(context, "功能待开发", 500);
        }
    }


}
