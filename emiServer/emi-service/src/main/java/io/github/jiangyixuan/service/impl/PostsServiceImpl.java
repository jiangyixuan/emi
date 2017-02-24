package io.github.jiangyixuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.Posts;
import io.github.jiangyixuan.mapper.PostsMapper;
import io.github.jiangyixuan.service.PostsService;

@Service
public class PostsServiceImpl implements PostsService{

	@Autowired
	private PostsMapper postsMapper;
	
	@Override
	public List<Posts> getPostsList() {
		// TODO Auto-generated method stub
		return postsMapper.getPostsList();
	}

	@Override
	public boolean insertPostsOne(String name, String title, String body, String time, String price, String address) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("title", title);
		map.put("body", body);
		map.put("time", time);
		map.put("time", price);
		map.put("address", address);
		
		return postsMapper.insertPostsOne(map);
	}

	@Override
	public boolean deletePostsOne(String sid, String name, String title) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("sid", sid);
		map.put("name", name);
		map.put("title", title);
		
		boolean result = postsMapper.deletePostsOne(map);
		postsMapper.deletePostsac(map);		
		
		return result;
	}

}
