package io.github.jiangyixuan.service;

import java.util.List;

import io.github.jiangyixuan.entity.PostsAc;
import io.github.jiangyixuan.entity.PostsAcInfo;

public interface PostsAcService {

public List<PostsAc> getPostAcList(String sid,String name);
	
	public boolean insertAll(String user,String body,String floor,String time,String name,String sid);

	public boolean insertOnly(String floor, String oneuser, String twouser,String name, String sid,String body);

	public List<PostsAcInfo> getPostsAcInfoList(String user, String floor,String name, String sid);
	
}
