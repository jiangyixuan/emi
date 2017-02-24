package io.github.jiangyixuan.service;

import java.util.List;
import java.util.Map;

import io.github.jiangyixuan.entity.SmallSort;

public interface SortDataService {

	Map<String, List<SmallSort>> getSortData();
	
}
