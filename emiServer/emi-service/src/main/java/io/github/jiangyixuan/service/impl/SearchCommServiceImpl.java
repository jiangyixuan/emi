package io.github.jiangyixuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.SearchComm;
import io.github.jiangyixuan.mapper.SearchCommMapper;
import io.github.jiangyixuan.service.SearchCommService;

@Service
public class SearchCommServiceImpl implements SearchCommService {

	@Autowired
	private SearchCommMapper searchCommMapper;
	
	@Override
	public List<SearchComm> getSearchComm(String keyword) {
		// TODO Auto-generated method stub
		System.out.println("----------------"+keyword+"-----------------");
		return searchCommMapper.getSearchCommByKeyword(keyword);
	}

}
