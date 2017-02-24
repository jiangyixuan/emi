package io.github.jiangyixuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.RecommendStoreOverview;
import io.github.jiangyixuan.mapper.RecommendStoreMapper;
import io.github.jiangyixuan.service.RecommendStoreService;

@Service
public class RecommendStoreServiceImpl implements RecommendStoreService{

	@Autowired
	private RecommendStoreMapper recommendStoreMapper;
	
	@Override
	public List<RecommendStoreOverview> getStoreOverviews(String flag, String smallSortId) {
		// TODO Auto-generated method stub
		
		List<RecommendStoreOverview> recommendStoreOverviews = null;
		
		if(flag.equals("all"))
		{
			recommendStoreOverviews = recommendStoreMapper.getStoreDataAll();
		}
		else if(flag.equals("smallSortId"))
		{
			recommendStoreOverviews = recommendStoreMapper.getStoreDataSmall(smallSortId);
		}
		
		return recommendStoreOverviews;
	}

}
