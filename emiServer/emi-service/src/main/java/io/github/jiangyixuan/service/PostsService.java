package io.github.jiangyixuan.service;

import java.util.List;

import io.github.jiangyixuan.entity.Posts;

public interface PostsService {

	// 获取论坛列表
	public List<Posts> getPostsList();

	public boolean insertPostsOne(String name, String title, String body, String time, String price, String address);

	public boolean deletePostsOne(String sid, String name, String title);

}
