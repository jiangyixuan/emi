package com.jyx.emi.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/29.
 */
public class DateUtils {

    /**
     * 字符串转换成日期型
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr){
        java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(dateStr);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期型转换成字符串型
     * @param date
     * @return
     */
    public static String dateToString(Date date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String str=sdf.format(date);
        return str;
    }

    /**
     * 将日期转换为毫秒
     */
    public static Long dateToLong(String str)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return millionSeconds;
    }


}
