package io.github.jiangyixuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.StoreMap;
import io.github.jiangyixuan.mapper.MapStoreMapper;
import io.github.jiangyixuan.service.MapStoreService;

@Service
public class MapStoreServiceImpl implements MapStoreService {

	@Autowired
	private MapStoreMapper mapStoreMapper;
	
	@Override
	public List<StoreMap> getMapStores(String flag, String value) {
		// TODO Auto-generated method stub
		
		List<StoreMap> storeMaps = null;
		if("all".equals(flag))
		{
			storeMaps = mapStoreMapper.getStoreMaps();
		}
		else if("search".equals(flag))
		{
			storeMaps = mapStoreMapper.getStoreMapsSearch(value);
		}
		
		return storeMaps;
	}

	@Override
	public String getStoreLanLag(String storeId) {
		// TODO Auto-generated method stub
		StringBuilder result = new StringBuilder();
		result.append(mapStoreMapper.getStoreLanLon(storeId));
		result.append("##");
		result.append(mapStoreMapper.getStoreLanLat(storeId));
	
		return result.toString();
	}

}
