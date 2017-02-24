package io.github.jiangyixuan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.User;
import io.github.jiangyixuan.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getUser",method=RequestMethod.POST)
	@ResponseBody
	public Object gerUser(String tel)
	{
		User user = userService.findUserByTel(tel);
		return user;
	}
	
	@RequestMapping(value="/insertUser",method=RequestMethod.POST)
	@ResponseBody
	public boolean insertUser(String tel,String pwd)
	{
		
		boolean result = false;
		
		try {
			
			if(userService.findUserByTel(tel)!=null)
			{
				result = userService.insertUser(tel, pwd);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			new Exception("该用户已经注册！！");
		}
		return result;
		
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public boolean updateUser(String userId,String updateType,String value)
	{
		return userService.updateUser(userId, updateType, value);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public boolean login(String telOrName,String pwd)
	{
		return userService.login(telOrName, pwd);
	}
	
}
