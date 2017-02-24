package io.github.jiangyixuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.AllStoreOverview;
import io.github.jiangyixuan.mapper.AllStoreMapper;
import io.github.jiangyixuan.service.AllStoreService;

@Service
public class AllStoreServiceImpl implements AllStoreService{

	@Autowired 
	private AllStoreMapper allStoreMapper;
	
	@Override
	public List<AllStoreOverview> getAllStoreOverviews() {
		// TODO Auto-generated method stub
		return allStoreMapper.getAllStoreOverviews();
	}

}
