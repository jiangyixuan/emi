package io.github.jiangyixuan.mapper;


import java.util.Map;

import io.github.jiangyixuan.entity.User;

public interface UserMapper {

	User findUserByTel(String tel);
	boolean insertUser(User user);
	boolean updateUser(Map<String,String> map);
	String chechUserByTel(String telOrName);
	String chechUserByName(String telOrName);
	
}