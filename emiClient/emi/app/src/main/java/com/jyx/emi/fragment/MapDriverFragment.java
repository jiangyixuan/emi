package com.jyx.emi.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.jyx.emi.R;
import com.jyx.emi.baidumap.LocationHelper;
import com.jyx.emi.imp.MapRouteCallBack;

/**
 * Created by Administrator on 2016/6/14.
 */
public class MapDriverFragment extends MapRouteBaseFragment implements View.OnClickListener,MapRouteCallBack {

    private ImageView iv_roadcondition;
    private boolean roadcondition = false;//路况被第几次点击，第一次点击false,第二次为true
    private PopupWindow popupWindow;
    private LinearLayout ll_less_traffic_light;//红绿灯少
    private LinearLayout ll_less_time;//时间最少
    private LinearLayout ll_other_program;//方案三
    private int defaultTextColor;

    RouteLine route = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initManager();
        View view = inflater.inflate(R.layout.fragment_map_driver, null);
        init(view);
        initData();

        return view;
    }

    private void init(View view) {
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        iv_roadcondition = (ImageView) view.findViewById(R.id.iv_roadcondition);
        baiduMap = mapView.getMap();

        iv_roadcondition.setOnClickListener(this);

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //弹出驾车路线窗口
                showDriverWindow();

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }


    private void initData() {
        requestLatLngFromServer(getActivity());
        //执行定位操作
        LocationHelper.initLocation(getActivity(), new MyLocationListener(), baiduMap, mLocationClient);

    }
    /**
     * 弹出窗口
     */
    private void showDriverWindow() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.window_map_driver, null);
        ll_less_traffic_light = (LinearLayout) view.findViewById(R.id.ll_less_traffic_light);
        ll_less_time= (LinearLayout) view.findViewById(R.id.ll_less_time);
        ll_other_program= (LinearLayout) view.findViewById(R.id.ll_other_program);
        ll_less_traffic_light.setOnClickListener(this);
        ll_less_time.setOnClickListener(this);
        ll_other_program.setOnClickListener(this);

        popupWindow = new PopupWindow(view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.route_drivet_window);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(getActivity().findViewById(R.id.tv_drive), Gravity.BOTTOM, 0, 0);

        defaultTextColor = ((TextView)ll_other_program.getChildAt(1)).getCurrentTextColor();

    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_roadcondition:
                iv_roadcondition.setImageResource(roadcondition ? R.mipmap.main_icon_roadcondition_on : R.mipmap.main_icon_roadcondition_off);
                baiduMap.setTrafficEnabled(roadcondition);//设置是否打开交通路况
                roadcondition = !roadcondition;
                break;

            //window窗口监听
            case R.id.ll_less_traffic_light:
                setWindowPressed(ll_less_traffic_light);
                setWindowNormal(ll_less_time);
                setWindowNormal(ll_other_program);
                break;
            case R.id.ll_less_time:
                setWindowPressed(ll_less_time);
                setWindowNormal(ll_less_traffic_light);
                setWindowNormal(ll_other_program);
                break;
            case R.id.ll_other_program:
                setWindowPressed(ll_other_program);
                setWindowNormal(ll_less_traffic_light);
                setWindowNormal(ll_less_time);
                break;

        }
    }

    /**
     * 将窗口背景，字体全部设为非选中状态
     */
    private void setWindowNormal(LinearLayout linearLayout)
    {
        linearLayout.setBackgroundColor(Color.WHITE);
        ((TextView)linearLayout.getChildAt(0)).setTextColor(defaultTextColor);
        ((TextView)linearLayout.getChildAt(1)).setTextColor(defaultTextColor);
        ((TextView)linearLayout.getChildAt(2)).setTextColor(defaultTextColor);
    }
    /**
     * 将窗口背景，字体全部设为选中状态
     */
    private void setWindowPressed(LinearLayout linearLayout)
    {
        linearLayout.setBackgroundResource(R.mipmap.route_driver_bac);
        ((TextView)linearLayout.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView)linearLayout.getChildAt(1)).setTextColor(Color.WHITE);
        ((TextView)linearLayout.getChildAt(2)).setTextColor(Color.WHITE);
    }

    /**
     * 规划路线
     */
    protected void routePlanSeaech(final LatLng startLatLng, final LatLng endLatLan) {
        //路线
        mSearch = RoutePlanSearch.newInstance();
        mSearch.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(startLatLng)).to(PlanNode.withLocation(endLatLan)));

        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    // result.getSuggestAddrInfo()
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {

                    route = drivingRouteResult.getRouteLines().get(0);
                    DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
                    routeOverlay = overlay;

                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                }
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {


            }
        });
    }

    @Override
    public void receive(LatLng startLatLng,LatLng endLatLan) {

        routePlanSeaech(startLatLng, endLatLan);

    }

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.home_press);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.home_press);
            }
            return null;
        }

    }
}
