package io.github.jiangyixuan.mapper;

import java.util.List;
import java.util.Map;

import io.github.jiangyixuan.entity.CommCollect;
import io.github.jiangyixuan.entity.CommOverview;

public interface CommOverviewMapper {

	//默认排序
	List<CommOverview> getCommOverviews(Map<String,String> map);
	//按价格下降排序
	List<CommOverview> getCommOverviewsByPriceDesc(Map<String,String> map);
	//按价格上升排序
	List<CommOverview> getCommOverviewsByPrice(Map<String,String> map);
	//按折扣上升排序
	List<CommOverview> getCommOverviewsByDiscount(Map<String,String> map);
	//按折扣下降排序
	List<CommOverview> getCommOverviewsByDiscountDesc(Map<String,String> map);
	
	List<CommOverview> getCollect(List<CommCollect> collects);
	
	List<CommOverview> getStoreCommOverviews(String storeId);
	
}
