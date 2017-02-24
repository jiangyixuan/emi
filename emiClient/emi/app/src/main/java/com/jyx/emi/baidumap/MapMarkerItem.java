package com.jyx.emi.baidumap;

/**
 * Created by Administrator on 2016/6/11.
 */

import com.baidu.mapapi.clusterutil.clustering.ClusterItem;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.jyx.emi.R;

/**
 * 每个Marker点，包含Marker点坐标以及图标,店铺名，品牌名，地址
 */
public class MapMarkerItem implements ClusterItem {
    private LatLng latLng;
    private int storeId;
    private String storeName;
    private String storePosition;
    private String brands;

    public MapMarkerItem(LatLng latLng,int storeId,String storeName,String storePosition,String brands) {
        this.latLng = latLng;
        this.storeId=storeId;
        this.storeName=storeName;
        this.storePosition=storePosition;
        this.brands=brands;
    }


    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding);
    }

    public int getStoreId() {
        return storeId;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getBrands() {
        return brands;
    }

    public String getStorePosition() {
        return storePosition;
    }

    @Override
    public String toString() {
        return "MapMarkerItem{" +
                "latLng=" + latLng +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", storePosition='" + storePosition + '\'' +
                ", brands='" + brands + '\'' +
                '}';
    }
}