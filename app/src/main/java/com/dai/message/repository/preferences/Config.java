package com.dai.message.repository.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.dai.message.bean.Music;
import com.google.gson.Gson;

public class Config {


    private static Config config;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private Gson gson = new Gson();

    private static final String COOKIE = "cookie";
    private static final String KEY_MUSIC = "music";
    private static final String KEY_IS_FIRST_PLAY = "is_first_play";

    private Config() {
    }

    public static Config getInstance() {
        if (config == null) {
            synchronized (Config.class) {
                if (config == null) {
                    return new Config();
                }
            }
        }
        return config;
    }

    @SuppressLint("CommitPrefEdits")
    public static void install(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public String getCookie() {
        return preferences.getString(COOKIE, null);
    }

    public void setCookie(String cookie) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(COOKIE, cookie);
        editor.apply();
    }


    public void setCurrentMusic(Music music) {
        editor.putString(KEY_MUSIC, gson.toJson(music));
        editor.apply();
    }

    public Music getCurrentMusic() {
        return gson.fromJson(preferences.getString(KEY_MUSIC, null), Music.class);
    }

    public void setIsFirstPlay(boolean isFirstPlay) {
        editor.putBoolean(KEY_IS_FIRST_PLAY, isFirstPlay);
        editor.apply();
    }

    public boolean isFirstPlay() {
        return preferences.getBoolean(KEY_IS_FIRST_PLAY, true);
    }

}
