package com.xlong.loserlivegame.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SLP on 2017/3/23.
 */

public class DB {
    private static DB dbCon;
    private SQLiteDatabase db;

    public DB(Context context) {
        db = new DataBaseHelper(context).getWritableDatabase();
    }

    /**
     * 获取当前类对象的方法
     *
     * @param context
     * @return
     */
    public static DB getNewInstance(Context context) {
        dbCon = new DB(context);
        return dbCon;
    }

    /**
     * 插入数据方法
     *
     * @param tbName 表名
     * @param values 表中要插入数据，列名+value构成的数据集
     * @return
     */
    public long insert(String tbName, ContentValues values) {
        return db.insert(tbName, null, values);
    }

    /**
     * 更新数据
     *
     * @param tbName      表名
     * @param values      要更新的列名+值构成的数据集
     * @param where       更新时的where条件
     * @param whereValues where条件表达式中 ? 占位符的值构成的集合
     * @return 更新的行数
     */
    public int update(String tbName, ContentValues values, String where, String[] whereValues) {
        return db.update(tbName, values, where, whereValues);
    }

    /**
     * 删除数据
     *
     * @param tbName      表名
     * @param where       删除时的where条件
     * @param whereValues where条件表达式中 ? 占位符的值构成的集合
     * @return
     */
    public int delete(String tbName, String where, String[] whereValues) {
        return db.delete(tbName, where, whereValues);
    }

    /**
     * 查询数据，查询表中所有列，只提供按照where条件进行查询，不支持group by
     *
     * @param tbName      表名
     * @param where       查询时的where条件
     * @param whereValues where条件表达式中 ? 占位符的值构成的集合
     * @param orderby     排序
     * @return
     */
    public Cursor query(String tbName, String where, String[] whereValues, String orderby) {
        return db.query(tbName, null, where, whereValues, null, null, orderby);
    }

    /**
     * 执行没有返回值的sql语句
     *
     * @param sql sql语句
     */
    public void execSQL(String sql) {
        db.execSQL(sql);
    }

    /**
     * 执行没有返回值的sql语句
     *
     * @param sql    sql语句
     * @param values sql语句中?占位符的值构成的数值
     */
    public void execSQL(String sql, Object[] values) {
        db.execSQL(sql, values);
    }

    /**
     * 执行有返回结果集的sql语句
     *
     * @param sql    sql语句
     * @param values sql语句中?占位符的值构成的数组，如果sql语句中没有?占位符，该参数可以给值为null
     * @return 结果集合的游标
     */
    public Cursor rawQuery(String sql, String[] values) {
        return db.rawQuery(sql, values);
    }

    public void closeDB() {
        if (null != db) {
            db.close();
        }
    }
}
