package com.jyx.emi.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.jyx.emi.activity.StoreMapActivity;
import com.jyx.emi.baidumap.SDKReceiver;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.imp.MapRouteCallBack;
import com.jyx.emi.utils.HttpUtils;

/**
 * Created by Administrator on 2016/6/14.
 */
public class MapRouteBaseFragment extends Fragment{

    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:

                    callBack = new MapDriverFragment();
                    callBack.receive(startLatLng,endLatLan);

                    break;

                default:break;
            }
        }
    };


    protected SDKReceiver sdkReceiver;
    protected MapView mapView;
    protected static BaiduMap baiduMap;
    protected LocationClient mLocationClient;
    private LatLng startLatLng;//当前坐标
    private LatLng endLatLan;
    public RoutePlanSearch mSearch = null;// 搜索模块，也可去掉地图模块独立使用

    private MapRouteCallBack callBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化这个地图
     */
    protected void initManager() {

        //不能传递Activity,必须是全局Context
        //拿到清单文件的key,校验服务器key
        SDKInitializer.initialize(getActivity().getApplicationContext());

        //注册广播监听者
        sdkReceiver = new SDKReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);//注册网络错误
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);//注册key校验结果

        getActivity().registerReceiver(sdkReceiver, filter);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());

    }

    /**
     * 定位请求回调接口
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation result) {
            //Receive Location
            if (result != null) {
                startLatLng = new LatLng(result.getLatitude(), result.getLongitude());

                MapStatusUpdate mapStatusUpdatePoint = MapStatusUpdateFactory.newLatLng(startLatLng);
                baiduMap.setMapStatus(mapStatusUpdatePoint);

                MyLocationData data = new MyLocationData.Builder().latitude(result.getLatitude())
                        .longitude(result.getLongitude()).build();

                baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

                baiduMap.setMyLocationData(data);

                if(endLatLan!=null){
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }

    protected void requestLatLngFromServer(Context context)
    {
        HttpUtils.doPostAsyn(GlobalContants.GET_LANLNG_URL, "storeId="+((StoreMapActivity) context).getStoreId(), new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {

                if (result != null) {
                    String[] lanLngs = result.split("##");
                    endLatLan = new LatLng(Double.parseDouble(lanLngs[1]), Double.parseDouble(lanLngs[0]));

                    if (startLatLng != null) {
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        });
    }


    @Override
    public void onPause() {
        mapView.onPause();
        mLocationClient.stop();
        getActivity().unregisterReceiver(sdkReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

}
