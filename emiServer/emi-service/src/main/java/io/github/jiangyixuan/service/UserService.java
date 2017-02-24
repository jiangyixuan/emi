package io.github.jiangyixuan.service;

import io.github.jiangyixuan.entity.User;

public interface UserService {
	
	/**
	 * 根据手机号查询用户
	 */
	public User findUserByTel(String tel);
	
	/**
	 * 注册用户
	 * @param tel 手机好
	 * @param pwd 密码
	 * @return
	 */
	public boolean insertUser(String tel,String pwd);
	
	/**
	 * 修改用户
	 * @param userId 用户id
	 * @param updateType 修改的字段
	 * @param value 修改后的值
	 * @return
	 */
	public boolean updateUser(String userId,String updateType,String value);
		

	public boolean login(String telOrName,String pwd);

}
