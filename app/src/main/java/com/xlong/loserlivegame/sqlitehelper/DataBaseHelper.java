package com.xlong.loserlivegame.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SLP on 2017/3/23.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private final static String DATABASENAME = "loserlive.db";
    private static final int DATABASERVERSION = 1;    // 设置数据库的版本

    public DataBaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASERVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String personData = "create table tb_person(pid integer primary key autoincrement, cash integer,  wallet integer, health smallint, " +
                "renown integer)";
        db.execSQL(personData);
        String goodsData = "create table tb_goods(gid integer primary key autoincrement, name text not null, cost integer not null, number integer not null)";
        db.execSQL(goodsData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
