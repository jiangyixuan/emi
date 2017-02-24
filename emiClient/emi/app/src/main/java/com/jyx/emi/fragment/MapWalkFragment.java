package com.jyx.emi.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapView;
import com.jyx.emi.R;

/**
 * Created by Administrator on 2016/6/14.
 */
public class MapWalkFragment extends MapRouteBaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initManager();
        View view = inflater.inflate(R.layout.fragment_map_walk, null);
        mapView = (MapView) view.findViewById(R.id.mapView);
        return view;
    }
}
