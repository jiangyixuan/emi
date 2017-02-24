package com.jyx.emi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/3.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME="data";
    private static final int VERSION=1;//数据库版本

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String sqlCollect="create table collect(id INTEGER PRIMARY KEY AUTOINCREMENT,commId int not null,time varChar(50) not null)";
            String sqlSearch="create table search(id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(20) not null)";
            db.execSQL(sqlCollect);
            db.execSQL(sqlSearch);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
