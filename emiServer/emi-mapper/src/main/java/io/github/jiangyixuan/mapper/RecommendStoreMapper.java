package io.github.jiangyixuan.mapper;

import java.util.List;

import io.github.jiangyixuan.entity.RecommendStoreOverview;

public interface RecommendStoreMapper {

	List<RecommendStoreOverview> getStoreDataAll();
	List<RecommendStoreOverview> getStoreDataSmall(String smallSortId);
	
}
