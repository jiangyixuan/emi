package io.github.jiangyixuan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.CommDetail;
import io.github.jiangyixuan.mapper.CommDetailMapper;
import io.github.jiangyixuan.service.CommDetailService;

@Service
public class CommDetailServiceImpl implements CommDetailService {

	@Autowired
	CommDetailMapper commDetailMapper;
	
	@Override
	public CommDetail getCommDetail(String commId) {
		// TODO Auto-generated method stub
		return commDetailMapper.getCommDetail(commId);
	}

}
