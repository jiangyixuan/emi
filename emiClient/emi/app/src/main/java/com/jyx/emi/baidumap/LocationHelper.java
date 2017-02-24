package com.jyx.emi.baidumap;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationConfiguration;

/**
 * Created by Administrator on 2016/6/14.
 */
public class LocationHelper {

    /**
     * 定位到当前位置
     * @param context
     * @param myLocationListener
     * @param baiduMap
     */
    public static void initLocation(Context context, BDLocationListener myLocationListener, BaiduMap baiduMap,LocationClient mLocationClient) {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);// 设置 LocationClientOption
        mLocationClient.registerLocationListener(myLocationListener);// 注册定位监听函数
        if (baiduMap != null) {
            MyLocationConfiguration configuration = new
                    MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,
                    true, null);//配置定位图层显示方式
            //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效，参见
//			 setMyLocationEnabled(boolean)
            baiduMap.setMyLocationConfigeration(configuration);

            baiduMap.setMyLocationEnabled(true);// 设置是否允许定位图层
            mLocationClient.start();
        }

    }

}


