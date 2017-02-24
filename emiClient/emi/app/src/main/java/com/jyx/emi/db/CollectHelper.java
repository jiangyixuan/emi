package com.jyx.emi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jyx.emi.bean.CommCollect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class CollectHelper {

    private SQLiteDatabase db;

    public CollectHelper(Context context) {
        if(db==null) {
            db=new DBHelper(context).getReadableDatabase();
        }
    }

    /**
     * 插入数据到收藏表
     * @param commId
     * @param time
     */
    public void insertCollect(String commId,String time)
    {
        String sql = "insert into collect(commId,time) values ("+commId+",'"+time+"')";
        db.execSQL(sql);//执行SQL语句
    }


    /**
     * 删除收藏表商品id为commId的信息
     * @param commId
     */
    public void deleteCollect(String commId)
    {
        String sql = "delete from collect where commId="+commId;
        db.execSQL(sql);
    }

    /**
     * 查询某个商品收藏信息
     * @return
     */
    public CommCollect selectCollect(String commId)
    {
        String sql = "select * from collect where commId = "+commId;
        Cursor cursor= db.rawQuery(sql,null);
        CommCollect collect=new CommCollect();
        if(cursor.moveToFirst())
        {
            collect.setId(cursor.getInt(0));
            collect.setCommId(cursor.getInt(1));
            collect.setTime(cursor.getString(2));
        }
        return collect;
    }


    /**
     * 返回所有收藏商品
     * @return
     */
    public List<CommCollect> getCommCollects()
    {
        List<CommCollect> commCollects = new ArrayList<CommCollect>();
        Cursor cursor = db.query("collect", null, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            int id= cursor.getInt(0);
            int commId = cursor.getInt(1);
            String time = cursor.getString(2);

            CommCollect collect = new CommCollect();
            collect.setId(id);
            collect.setCommId(commId);
            collect.setTime(time);
            commCollects.add(collect);
        }

        return commCollects;
    }


}
