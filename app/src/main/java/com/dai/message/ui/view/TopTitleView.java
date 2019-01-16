package com.dai.message.ui.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dai.message.R;
import com.dai.message.bean.Music;
import com.dai.message.callback.LocalCallback;
import com.dai.message.util.SimpleTextWatcher;
import com.dai.message.util.ToastUtil;

/**
 * created by dht on 2019/1/15 11:10
 */
public class TopTitleView extends LinearLayout implements View.OnClickListener {


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

    public TopTitleView(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public TopTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    public TopTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        initView();
    }

    /**
     * 初始化视图View
     */
    private void initView() {
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
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 设置文本框监听回调事件
     *
     * @param textWatcher SimpleTextWatcher
     */
    public void setSearchEditTextWatcher(SimpleTextWatcher textWatcher) {
        searchEdit.addTextChangedListener(new SimpleTextWatcher());
    }

    /**
     * 设置分享按钮的回调接口
     *
     * @param localCallback 本地回调接口
     */
    public void setSharedCallback(final LocalCallback<String> localCallback) {
        shared.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                localCallback.onChangeData(null);
            }
        });
    }

    /**
     * 更新视图View
     */
    public void updateView(final String value) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(value);
            }
        });
    }

    /**
     * 更新视图View
     *
     * @param music Music实体数据
     */
    public void updatePlayView(final Music music) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playRelative.setVisibility(VISIBLE);
                songName.setText(music.name);
                author.setText(music.author);
            }
        });
    }

    /**
     * 更新视图View是否可见
     */
    public void setViewVisibility() {
        title.setVisibility(VISIBLE);
        search.setVisibility(VISIBLE);
        setting.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_title_back:
                activity.finish();
                break;

            case R.id.top_search:
                boolean isSearch = context.getString(R.string.search).equals(search.getText().toString());
                title.setVisibility(isSearch ? GONE : VISIBLE);
                searchEdit.setVisibility(isSearch ? VISIBLE : GONE);
                search.setText(isSearch ? R.string.close_search : R.string.search);
                break;
            case R.id.top_setting:
                ToastUtil.toastCustom(context, "功能待开发", 500);
                break;
        }
    }


}
