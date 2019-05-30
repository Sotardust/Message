package com.dht.databaselib.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.dht.databaselib.bean.music.MusicBean;
import com.google.gson.Gson;

/**
 * @author Administrator
 */
public class MessagePreferences {

    private static MessagePreferences instance;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private Gson gson = new Gson();

    private static final String COOKIE = "cookie";
    /**
     * 保存当前播放音乐
     */
    private static final String KEY_MUSIC = "music";
    /**
     * 设置是否是第一次播放
     */
    private static final String KEY_IS_FIRST_PLAY = "is_first_play";
    /**
     * 设置播放类型
     */
    private static final String KEY_PLAY_TYPE = "play_type";
    /**
     * 设置用户id
     */
    private static final String KEY_PERSON_ID = "person_id";

    /**
     * 保存前播放状态
     */
    private static final String KEY_IS_PLAYING = "is_playing";

    private MessagePreferences () {
    }

    public static MessagePreferences getInstance () {
        if (instance == null) {
            synchronized (MessagePreferences.class) {
                if (instance == null) {
                    return new MessagePreferences();
                }
            }
        }
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    public static void install (Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("message_preferences", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public String getCookie () {
        return preferences.getString(COOKIE, null);
    }

    public void setCookie (String cookie) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(COOKIE, cookie);
        editor.apply();
    }


    public void setCurrentMusic (MusicBean music) {
        editor.putString(KEY_MUSIC, gson.toJson(music));
        editor.apply();
    }

    public MusicBean getCurrentMusic () {
        return gson.fromJson(preferences.getString(KEY_MUSIC, null), MusicBean.class);
    }

    public void setPlayType (int playType) {
        editor.putInt(KEY_PLAY_TYPE, playType);
        editor.apply();
    }

    public int getPlayType () {
        return preferences.getInt(KEY_PLAY_TYPE, 0);
    }


    public void setFirstPlay (boolean isFirstPlay) {
        editor.putBoolean(KEY_IS_FIRST_PLAY, isFirstPlay);
        editor.apply();
    }

    public boolean isFirstPlay () {
        return preferences.getBoolean(KEY_IS_FIRST_PLAY, true);
    }

    public void setPlaying (boolean isPlaying) {
        editor.putBoolean(KEY_IS_PLAYING, isPlaying);
        editor.apply();
    }

    public boolean isPlaying () {
        return preferences.getBoolean(KEY_IS_PLAYING, false);
    }

    public void setPersonId (long personId) {
        editor.putLong(KEY_PERSON_ID, personId);
        editor.apply();
    }

    public long getPersonId () {
        return preferences.getLong(KEY_PERSON_ID, 123L);
    }

}
