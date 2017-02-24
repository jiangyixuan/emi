package com.jyx.emi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jyx.emi.bean.CommCollect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class SearchHistoryHelper {

    private SQLiteDatabase db;
    public SearchHistoryHelper(Context context) {
        if(db==null) {
            db=new DBHelper(context).getReadableDatabase();
        }
    }

    public void insertSearch(String name)
    {
        String sql = "insert into search(name) values ('"+name+"')";
        db.execSQL(sql);//执行SQL语句
    }

    public void deleteAllSearch()
    {
        String sql="delete from search";
        db.execSQL(sql);
    }

    public List<String> getSearchHistory()
    {
        List<String> strings = new ArrayList<String>();
        Cursor cursor = db.query("search", null, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            strings.add(name);
        }
        return strings;
    }

}
