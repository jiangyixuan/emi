package io.github.jiangyixuan.mapper;

import java.util.List;
import java.util.Map;

import io.github.jiangyixuan.entity.Posts;

public interface PostsMapper {
	
	List<Posts> getPostsList();
	boolean insertPostsOne(Map<String,String> map);
	boolean deletePostsOne(Map<String,String> map);
	boolean deletePostsac(Map<String,String> map);
}
