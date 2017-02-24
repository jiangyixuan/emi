package com.jyx.emi.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jyx.emi.R;
import com.jyx.emi.activity.StoreActivity;
import com.jyx.emi.activity.StoreMapActivity;
import com.jyx.emi.baidumap.MapHelper;
import com.jyx.emi.baidumap.MapMarkerItem;
import com.jyx.emi.baidumap.SDKReceiver;
import com.jyx.emi.bean.StoreMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/10.
 */
public class MapBaseFragment extends Fragment {


    protected SDKReceiver sdkReceiver;
    protected MapView mapView;
    protected BaiduMap baiduMap;
    protected LocationClient mLocationClient;
    protected MapStatus mapStatus;
    protected ClusterManager<MapMarkerItem> mClusterManager;
    protected LatLng endLatLng;//点击的item坐标
    private LatLng startLatLng;//当前位置坐标
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 校验KEY
        initManager();
        View view = inflater.inflate(R.layout.fragment_go, null);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        if (popupWindow != null) {
            popupWindow.dismiss();//fragment不可见时隐藏fragment
        }
        getActivity().unregisterReceiver(sdkReceiver);
        mapView.onPause();
        mLocationClient.stop();
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

            }
        }
    }

    /**
     * 向地图添加Marker点
     */
    public void addMarkers(List<StoreMap> storeMaps) {

        // 添加Marker点
        List<MapMarkerItem> items = new ArrayList<MapMarkerItem>();

        for (int i = 0; i < storeMaps.size(); i++) {
            StoreMap storeMap = storeMaps.get(i);
            String lat = storeMap.getStoreLat();
            String lon = storeMap.getStoreLon();

            LatLng ll = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

            items.add(new MapMarkerItem(ll, storeMap.getStoreId(), storeMap.getStoreName()
                    , storeMap.getStorePosition(), storeMap.getBrands()));
        }
        mClusterManager.addItems(items);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MapMarkerItem>() {
            @Override
            public boolean onClusterItemClick(MapMarkerItem item) {

                //弹出底部窗口
                double distance = new MapHelper().getDistance(startLatLng, item.getPosition());
                showMapWindow(item, distance);

                return true;
            }
        });
        baiduMap.setOnMarkerClickListener(mClusterManager);

    }

    /**
     * 显示地图显示数据
     */
    protected void showMapData(List<StoreMap> storeMaps) {
        //清空地图所有的 Overlay 覆盖物以及 InfoWindow
        baiduMap.clear();
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapStatus = new MapStatus.Builder().zoom(12).build();
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
            }
        });
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MapMarkerItem>(getActivity(), baiduMap);
        // 添加Marker点
        addMarkers(storeMaps);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        baiduMap.setOnMapStatusChangeListener(mClusterManager);

    }

    /**
     * 显示底部窗口
     */
    private void showMapWindow(final MapMarkerItem item, double distance) {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.store_window, null);

        TextView tv_go_store = (TextView) view.findViewById(R.id.tv_go_store);
        TextView tv_go = (TextView) view.findViewById(R.id.tv_go);

        tv_go_store.setText("进入" + item.getStoreName());
        ((TextView) view.findViewById(R.id.tv_store_name)).setText(item.getStoreName());
        ((TextView) view.findViewById(R.id.tv_distance)).setText(distance + "km");
        ((TextView) view.findViewById(R.id.tv_store_position)).setText(item.getStorePosition());
                                                        //
        popupWindow = new PopupWindow(view, FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT );
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });
        popupWindow.showAtLocation(getActivity().findViewById(R.id.iv_menu), Gravity.BOTTOM, 0, 0);

        //进店
        tv_go_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StoreActivity.class);
                intent.putExtra("storeId", item.getStoreId() + "");
                intent.putExtra("brands", item.getBrands());
                startActivity(intent);
            }
        });
        //到这去
        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StoreMapActivity.class);
                intent.putExtra("storeId",item.getStoreId() + "");
                startActivity(intent);
            }
        });
    }

}
