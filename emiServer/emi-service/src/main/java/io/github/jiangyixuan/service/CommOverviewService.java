package io.github.jiangyixuan.service;

import java.util.List;

import io.github.jiangyixuan.entity.CommCollect;
import io.github.jiangyixuan.entity.CommOverview;

public interface CommOverviewService {
	
	List<CommOverview> getCommOverviews(String smallSortId,String top,String sortby);
	
	List<CommOverview> getCollect(List<CommCollect> collects);
	
	List<CommOverview> getStoreCommOverviews(String storeId);

}
