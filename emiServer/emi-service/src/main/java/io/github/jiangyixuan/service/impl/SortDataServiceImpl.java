package io.github.jiangyixuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.CommsortBig;
import io.github.jiangyixuan.entity.SmallSort;
import io.github.jiangyixuan.mapper.SortDataMapper;
import io.github.jiangyixuan.service.SortDataService;

@Service
public class SortDataServiceImpl implements SortDataService{

	@Autowired
	private SortDataMapper sortDataMapper;
	
	@Override
	public Map<String, List<SmallSort>> getSortData() {
		// TODO Auto-generated method stub
		Map<String, List<SmallSort>> map=new HashMap<String, List<SmallSort>>();
		List<CommsortBig> commsortBigs = sortDataMapper.getCommsortBigs();
		
		for(CommsortBig commsortBig:commsortBigs)
		{
			List<SmallSort> smallSorts = sortDataMapper.getSmallSorts(commsortBig.getBigSortId());
			if(smallSorts.size()!=0)
			{
				map.put(commsortBig.getBigSortName(),smallSorts);
			}
		}
		return map;
	}

}
