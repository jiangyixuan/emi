package io.github.jiangyixuan.service;

import java.util.List;

import io.github.jiangyixuan.entity.StoreMap;

public interface MapStoreService {

	List<StoreMap> getMapStores(String flag,String value);
	
	String getStoreLanLag(String storeId);	
	
}
