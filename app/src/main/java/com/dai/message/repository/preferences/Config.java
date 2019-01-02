package com.dai.message.repository.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {


    private static Config config;
    private static SharedPreferences preferences;
    private static final String COOKIE = "cookie";

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

    public static void install(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public String getCookie() {
        return preferences.getString(COOKIE, null);
    }

    public void setCookie(String cookie) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(COOKIE, cookie);
        editor.apply();
    }

}
