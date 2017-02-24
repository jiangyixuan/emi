package io.github.jiangyixuan.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.jiangyixuan.entity.User;
import io.github.jiangyixuan.mapper.UserMapper;
import io.github.jiangyixuan.service.UserService;

@Service
public class UserSerciceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findUserByTel(String tel) {
		// TODO Auto-generated method stub
		
		//代码缺少业务逻辑。健壮性不够，客户端来判断返回数据
		return userMapper.findUserByTel(tel);
	}

	@Override
	public boolean insertUser(String tel, String pwd) {
		// TODO Auto-generated method stub
		
		User user = new User();
		user.setTel(tel);
		user.setPwd(pwd);
		
		return userMapper.insertUser(user);
	}

	@Override
	public boolean updateUser(String userId, String updateType, String value) {
		// TODO Auto-generated method stub
		
		Map<String,String> map = new HashMap<String,String>(); 
		map.put("userId", userId);
		map.put("updateType", updateType);
		map.put("value", value);
		
		return userMapper.updateUser(map);
	}

	@Override
	public boolean login(String telOrName, String pwd) {
		// TODO Auto-generated method stub
		
		if(pwd.equals(userMapper.chechUserByTel(telOrName))||pwd.equals(userMapper.chechUserByName(telOrName))){
			return true;
		}
		return false;
	}

}
