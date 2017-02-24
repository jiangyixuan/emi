package io.github.jiangyixuan.mapper;

import java.util.List;
import java.util.Map;

import io.github.jiangyixuan.entity.PostsAc;
import io.github.jiangyixuan.entity.PostsAcInfo;

public interface PostsAcMapper {
	
	List<PostsAc> getPostsAcList(Map<String,String> map);
	boolean insertAll(Map<String,String> map);
	boolean insertOnly(Map<String,String> map);
	List<PostsAcInfo> getPostsAcInfoList(Map<String,String> map);
}
