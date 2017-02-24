package com.jyx.emi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * 跟网络相关的工具类
 * Created by Administrator on 2016/4/30.
 */
public class NetUtils {
    private NetUtils() {
        /* */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity!=null)
        {
            NetworkInfo info=connectivity.getActiveNetworkInfo();
            if(info!=null&&info.isConnected())
            {
                if(info.getState()== NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm=(ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm==null)
        {
            return false;
        }
        return cm.getActiveNetworkInfo().getType()==ConnectivityManager.TYPE_WIFI;
    }
    /**
     * 打开网络设置页面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }
}
