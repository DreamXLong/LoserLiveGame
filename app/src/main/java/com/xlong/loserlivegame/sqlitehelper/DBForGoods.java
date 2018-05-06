package com.xlong.loserlivegame.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xlong.loserlivegame.model.BuyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SLP on 2017/3/23.
 */

public class DBForGoods {
    private final String COLUMN_ID = "gid";
    private final String TABLE_NAME = "tb_goods";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_COST = "cost";
    private final String COLUMN_NUMBER = "number";
    private DB db;
    private ContentValues values;

    public DBForGoods(Context context) {
        db = DB.getNewInstance(context);
        values = new ContentValues();
    }

    public long addGoods(BuyModel buyModel) {
        values.clear();
        values.put(COLUMN_ID, buyModel.getId());
        values.put(COLUMN_NAME, buyModel.getName());
        values.put(COLUMN_COST, buyModel.getPrize());
        values.put(COLUMN_NUMBER, buyModel.getNum());
        return db.insert(TABLE_NAME, values);
    }

    public List<BuyModel> getGoods() {
        List<BuyModel> buyModels = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            buyModels.add(new BuyModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
        }
        return buyModels;
    }

    public List<String> getGoodName() {
        List<String> name = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            name.add(cursor.getString(1));
        }
        return name;
    }

    public int updateGoods(String name, int prize, int num) {
        values.clear();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COST, prize);
        values.put(COLUMN_NUMBER, num);
        return db.update(TABLE_NAME, values, COLUMN_NAME + "=?", new String[]{name});
    }

    public void deleteGoods() {
        String sql = "delete from " + TABLE_NAME;
        db.execSQL(sql);
    }
}
