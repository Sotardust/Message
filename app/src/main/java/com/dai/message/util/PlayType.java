package com.dai.message.util;

/**
 * created by dht on 2019/1/14 16:46
 *
 * @author Administrator
 */
public enum PlayType {
    //顺序播放
    PLAY_IN_ORDER(0),
    //单曲循环
    SINGLE_CYCLE(1),
    //列表循环
    LIST_LOOP(2),
    //随机播放
    SHUFFLE_PLAYBACK(3);

    private int index;


    PlayType (int index) {
        this.index = index;
    }

    public int getIndex () {
        return index;
    }

    public static PlayType getPlayType (int index) {
        switch (index) {
            case 0:
                return PLAY_IN_ORDER;
            case 1:
                return SINGLE_CYCLE;
            case 2:
                return LIST_LOOP;
            default:
                return SHUFFLE_PLAYBACK;
        }
    }

    public static String getPlayTypeString (int index) {
        switch (index) {
            case 0:
                return "顺序播放";
            case 1:
                return "单曲循环";
            case 2:
                return "列表循环";
            default:
                return "随机播放";
        }
    }
}
