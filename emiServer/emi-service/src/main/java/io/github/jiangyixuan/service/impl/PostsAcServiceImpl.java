package io.github.jiangyixuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.PostsAc;
import io.github.jiangyixuan.entity.PostsAcInfo;
import io.github.jiangyixuan.mapper.PostsAcMapper;
import io.github.jiangyixuan.service.PostsAcService;

@Service
public class PostsAcServiceImpl implements PostsAcService {

	@Autowired
	private PostsAcMapper postsAcMapper;
	
	@Override
	public List<PostsAc> getPostAcList(String sid, String name) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("sid", sid);
		map.put("name", name);
		
		return postsAcMapper.getPostsAcList(map);
	}

	@Override
	public boolean insertAll(String user, String body, String floor, String time, String name, String sid) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("user", user);
		map.put("body", body);
		map.put("floor", floor);
		map.put("time", time);
		map.put("name", name);
		map.put("sid", sid);
		
		return postsAcMapper.insertAll(map);
	}

	@Override
	public boolean insertOnly(String floor, String oneuser, String twouser, String name, String sid, String body) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("floor", floor);
		map.put("oneuser", oneuser);
		map.put("twouser", twouser);
		map.put("name", name);
		map.put("sid", sid);
		map.put("body", body);
		
		return postsAcMapper.insertOnly(map);
	}

	@Override
	public List<PostsAcInfo> getPostsAcInfoList(String user, String floor, String name, String sid) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("user", user);
		map.put("floor", floor);
		map.put("name", name);
		map.put("sid", sid);
		
		return postsAcMapper.getPostsAcInfoList(map);
	}

}
