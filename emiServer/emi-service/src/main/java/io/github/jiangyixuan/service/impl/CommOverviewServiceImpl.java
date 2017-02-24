package io.github.jiangyixuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.CommCollect;
import io.github.jiangyixuan.entity.CommOverview;
import io.github.jiangyixuan.mapper.CommOverviewMapper;
import io.github.jiangyixuan.service.CommOverviewService;

@Service
public class CommOverviewServiceImpl implements CommOverviewService{

	@Autowired
	private CommOverviewMapper commOverviewMapper;
	
	@Override
	public List<CommOverview> getCommOverviews(String smallSortId, String top, String sortby) {
		// TODO Auto-generated method stub
		
		List<CommOverview> commOverviews = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("smallSortId", smallSortId);
		map.put("top", top);
		
		switch (sortby) {
		case "0":
			System.out.println("getCommOverviews");
			commOverviews = commOverviewMapper.getCommOverviews(map);
			break;
		case "1":
			commOverviews = commOverviewMapper.getCommOverviewsByPriceDesc(map);
			break;
		case "2":
			commOverviews = commOverviewMapper.getCommOverviewsByPrice(map);
			break;
		case "3":
			commOverviews = commOverviewMapper.getCommOverviewsByDiscount(map);
			break;
		case "4":
			commOverviews = commOverviewMapper.getCommOverviewsByDiscountDesc(map);
			break;
	
		default:
			break;
		}
		return commOverviews;
	}

	@Override
	public List<CommOverview> getCollect(List<CommCollect> collects) {
		// TODO Auto-generated method stub
		
		return commOverviewMapper.getCollect(collects);
	}

	@Override
	public List<CommOverview> getStoreCommOverviews(String storeId) {
		// TODO Auto-generated method stub
		return commOverviewMapper.getStoreCommOverviews(storeId);
	}

}
