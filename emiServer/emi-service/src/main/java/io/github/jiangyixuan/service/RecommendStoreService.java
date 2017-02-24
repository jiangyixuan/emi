package io.github.jiangyixuan.service;

import java.util.List;

import io.github.jiangyixuan.entity.RecommendStoreOverview;

public interface RecommendStoreService {

	List<RecommendStoreOverview> getStoreOverviews(String flag,String smallSortId);
	
}
