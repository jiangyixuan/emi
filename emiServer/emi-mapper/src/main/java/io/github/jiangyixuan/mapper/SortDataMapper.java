package io.github.jiangyixuan.mapper;


import java.util.List;

import io.github.jiangyixuan.entity.CommsortBig;
import io.github.jiangyixuan.entity.SmallSort;

public interface SortDataMapper {

	List<CommsortBig> getCommsortBigs();
	
	List<SmallSort> getSmallSorts(Integer bigSortId);
	
}