package com.dai.message.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by Administrator on 2019/1/10 16:55
 */

@Entity(tableName = "music", indices = {@Index("id")})
public class Music implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    /*歌名*/
    @ColumnInfo(name = "name")
    public String name;

    /*歌曲路径*/
    @ColumnInfo(name = "path")
    public String path;

    /*歌手*/
    @ColumnInfo(name = "author")
    public String author;

    /*歌曲对应图像*/
    @ColumnInfo(name = "avatar")
    public String avatar;

    /*歌词*/
    @ColumnInfo(name = "lyrics")
    public String lyrics;

    /*歌曲类型*/
    @ColumnInfo(name = "type")
    public String type;

    public Music() {

    }

    private Music(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        author = in.readString();
        avatar = in.readString();
        lyrics = in.readString();
        type = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(author);
        dest.writeString(avatar);
        dest.writeString(lyrics);
        dest.writeString(type);
    }

    @Override
    public String toString() {
        return "id = " + id +
                "name = " + name +
                "author = " + author +
                "path = " + path +
//                "avatar = " + avatar +
//                "lyrics = " + lyrics +
                "type = " + type;
    }
}
