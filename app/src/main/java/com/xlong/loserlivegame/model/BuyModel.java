package com.xlong.loserlivegame.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SLP on 2017/3/16.
 */

public class BuyModel implements Parcelable {
    private int id;
    private String name;
    private int prize;
    private int num;

    public BuyModel(int id, String name, int prize, int num) {
        this.id = id;
        this.name = name;
        this.prize = prize;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "BuyModel{" +
                "name='" + name + '\'' +
                ", prize=" + prize +
                ", num=" + num +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.prize);
        dest.writeInt(this.num);
    }

    protected BuyModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.prize = in.readInt();
        this.num = in.readInt();
    }

    public static final Parcelable.Creator<BuyModel> CREATOR = new Parcelable.Creator<BuyModel>() {
        @Override
        public BuyModel createFromParcel(Parcel source) {
            return new BuyModel(source);
        }

        @Override
        public BuyModel[] newArray(int size) {
            return new BuyModel[size];
        }
    };
}
