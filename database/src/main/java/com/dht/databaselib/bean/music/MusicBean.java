package com.dht.databaselib.bean.music;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by Administrator on 2019/1/10 16:55
 *
 * @author Administrator
 */

@Entity(tableName = "music")
public class MusicBean implements Parcelable {

    @PrimaryKey()
    @ColumnInfo(name = "code")
    public int code;

    /**
     * 歌名
     */
    @ColumnInfo(name = "name")
    public String name;

    /**
     * 歌曲路径
     */

    @ColumnInfo(name = "path")
    public String path;

    /**
     * 歌手
     */
    @ColumnInfo(name = "author")
    public String author;

    /**
     * 歌曲对应图像
     */
    @ColumnInfo(name = "avatar")
    public String avatar;

    /**
     * 歌词
     */
    @ColumnInfo(name = "lyrics")
    public String lyrics;

    /**
     * 歌曲类型
     */
    @ColumnInfo(name = "type")
    public String type;

    public MusicBean () {

    }

    protected MusicBean(Parcel in) {
        code = in.readInt();
        name = in.readString();
        path = in.readString();
        author = in.readString();
        avatar = in.readString();
        lyrics = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(author);
        dest.writeString(avatar);
        dest.writeString(lyrics);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };
}
