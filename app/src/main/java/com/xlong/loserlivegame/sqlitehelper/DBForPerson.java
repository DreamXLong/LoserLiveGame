package com.xlong.loserlivegame.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xlong.loserlivegame.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SLP on 2017/3/23.
 */

public class DBForPerson {
    private final String TABLE_NAME = "tb_person";
    private final String COLUMN_ID = "pid";
    private final String COLUMN_CASH = "cash";
    private final String COLUMN_WALLET = "wallet";
    private final String COLUMN_HEALTH = "health";
    private final String COLUMN_RENOWN = "renown";
    private DB db;
    private ContentValues valuse;

    public DBForPerson(Context context) {
        db = DB.getNewInstance(context);
        valuse = new ContentValues();
    }

    /**
     * 插入玩家信息
     *
     * @param person
     * @return
     */
    public long addPerson(Person person) {
        valuse.clear();
        valuse.put(COLUMN_ID, person.getId());
        valuse.put(COLUMN_CASH, person.getCash());
        valuse.put(COLUMN_WALLET, person.getWallet());
        valuse.put(COLUMN_HEALTH, person.getHealth());
        valuse.put(COLUMN_RENOWN, person.getRenown());
        return db.insert(TABLE_NAME, valuse);
    }

    /**
     * 根据玩家id删除信息
     *
     * @param id
     * @return
     */
    public int deletePersonById(int id) {
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int updatePerson(int id, Integer cash, Integer wallet, Integer health, Integer renown) {
        valuse.clear();
        valuse.put(COLUMN_ID, id);
        valuse.put(COLUMN_CASH, cash);
        valuse.put(COLUMN_WALLET, wallet);
        valuse.put(COLUMN_HEALTH, health);
        valuse.put(COLUMN_RENOWN, renown);
        return db.update(TABLE_NAME, valuse, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Person getPerson() {
        Person person = null;
        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            person = new Person(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        }
        return person;
    }

    public void deletePerson() {
        String sql = "delete from " + TABLE_NAME;
        db.execSQL(sql);
    }
}
