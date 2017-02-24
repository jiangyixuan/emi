package io.github.jiangyixuan.mapper;

import java.util.List;

import io.github.jiangyixuan.entity.StoreMap;

public interface MapStoreMapper {

	List<StoreMap> getStoreMaps();
	List<StoreMap> getStoreMapsSearch(String value);
	String getStoreLanLon(String storeId);
	String getStoreLanLat(String storeId);
	
}
