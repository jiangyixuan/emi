package com.jyx.emi.imp;

import com.baidu.mapapi.model.LatLng;

/**
 * 百度地图回调接口，用于操作完成后通知对方
 * Created by Administrator on 2016/6/17.
 */
public interface MapRouteCallBack {

    void receive(LatLng startLatLng,LatLng endLatLan);

}
