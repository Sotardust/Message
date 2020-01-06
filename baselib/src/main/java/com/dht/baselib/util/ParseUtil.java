package com.dht.baselib.util;

/**
 * 歌名解析工具
 * <p>
 * created by dht on 2020/1/6 18:18
 */
public class ParseUtil {

    /**
     * 解析歌曲名称
     *
     * @param name 文件名
     * @return 歌名
     */
    public static String parseSongName(String name) {
        if (name == null) {
            return null;
        }
        String[] strings = name.split("-");
        String[] songName = strings[strings.length - 1].split("\\.");
        return songName[0];
    }

    /**
     * 解析歌手名
     *
     * @param name 文件名
     * @return 歌手名
     */
    public static String parseAuthor(String name) {
        if (name == null) {
            return null;
        }
        String[] username = name.split("-");
        return username[0];
    }

    /**
     * 解析歌曲类型
     *
     * @param name 文件名
     * @return 歌曲类型
     */
    public static String parseType(String name) {
        if (name == null) {
            return null;
        }
        String[] type = name.split("\\.");
        return type[1];
    }
}
