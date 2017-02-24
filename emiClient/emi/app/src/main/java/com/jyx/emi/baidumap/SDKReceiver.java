package com.jyx.emi.baidumap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.jyx.emi.R;

/**
 * Created by Administrator on 2016/6/12.
 */
public  /*
	 * 百度地图监听广播
	 */
class SDKReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String result=intent.getAction();
        if(result.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR))
        {
            //网络错误
            Toast.makeText(context, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
        else if(result.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR))
        {
            //校验key失败
            Toast.makeText(context,R.string.check_key_fail, Toast.LENGTH_SHORT).show();
        }
    }

}