package io.github.jiangyixuan.service;

import java.util.List;

import io.github.jiangyixuan.entity.SearchComm;

public interface SearchCommService {

	public List<SearchComm> getSearchComm(String keyword);
	
}
