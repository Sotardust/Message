package com.dai.message.util;

/**
 * created by Administrator on 2019/1/14 16:46
 */
public enum PlayType {

    SINGLE_CYCLE(0),//单曲循环

    PLAY_IN_ORDER(1), //顺序播放

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
}
