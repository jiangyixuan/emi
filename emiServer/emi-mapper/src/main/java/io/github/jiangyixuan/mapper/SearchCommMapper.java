package io.github.jiangyixuan.mapper;

import java.util.List;

import io.github.jiangyixuan.entity.SearchComm;

public interface SearchCommMapper {
	
	List<SearchComm> getSearchCommByKeyword(String keyword);
	
}
