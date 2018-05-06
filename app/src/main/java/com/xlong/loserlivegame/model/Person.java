package com.xlong.loserlivegame.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SLP on 2017/3/23.
 */

public class Person implements Parcelable {
    private int id;
    private int cash;
    private int wallet;
    private int health;
    private int renown;

    public Person(int id, int cash, int wallet, int health, int renown) {
        this.id = id;
        this.cash = cash;
        this.wallet = wallet;
        this.health = health;
        this.renown = renown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRenown() {
        return renown;
    }

    public void setRenown(int renown) {
        this.renown = renown;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", cash=" + cash +
                ", wallet=" + wallet +
                ", health=" + health +
                ", renown=" + renown +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.cash);
        dest.writeInt(this.wallet);
        dest.writeInt(this.health);
        dest.writeInt(this.renown);
    }

    protected Person(Parcel in) {
        this.id = in.readInt();
        this.cash = in.readInt();
        this.wallet = in.readInt();
        this.health = in.readInt();
        this.renown = in.readInt();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
