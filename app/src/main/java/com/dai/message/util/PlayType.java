package com.dai.message.util;

/**
 * created by dht on 2019/1/14 16:46
 */
public enum PlayType {

    PLAY_IN_ORDER(0), //顺序播放

    SINGLE_CYCLE(1),//单曲循环

    LIST_LOOP(2),//列表循环

    SHUFFLE_PLAYBACK(3);//随机播放

    private int index;


    PlayType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static PlayType getPlayType(int index) {
        switch (index) {
            case 0:
                return SINGLE_CYCLE;
            case 1:
                return PLAY_IN_ORDER;
            case 2:
                return LIST_LOOP;
            default:
                return SHUFFLE_PLAYBACK;
        }
    }

    public static String getPlayTypeString(int index) {
        switch (index) {
            case 0:
                return "单曲循环";
            case 1:
                return "顺序播放";
            case 2:
                return "列表循环";
            default:
                return "随机播放";
        }
    }
}
