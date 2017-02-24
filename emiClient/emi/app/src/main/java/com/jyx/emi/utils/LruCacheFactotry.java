package com.jyx.emi.utils;

import android.util.LruCache;

/**
 * Created by Administrator on 2016/5/26.
 */
public class LruCacheFactotry {

    private static LruCache<String,String> cache=null;

    public static LruCache<String,String> getInstace()
    {
        if(cache==null)
        {
            cache=new LruCache<String,String>((int) Runtime.getRuntime().maxMemory()/8);
        }
        return cache;
    }
    

}
