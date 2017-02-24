package io.github.jiangyixuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.service.PostsAcService;

@Controller
public class PostsAcController {

	@Autowired
	private PostsAcService postsAcService;
	
	@RequestMapping(value="/postsAcCl",method=RequestMethod.POST)
	@ResponseBody
	public Object postsAcCl(String action,String sid,String name,
			String user,String body,String floor,String time,String oneuser,String twouser)
	{
		if("select".equals(action))
		{
			return postsAcService.getPostAcList(sid, name);
		}
		else if("insertAll".equals(action))
		{
			return postsAcService.insertAll(user, body, floor, time, name, sid);
		}
		else if("insertOnly".equals(action))
		{
			return postsAcService.insertOnly(floor,oneuser,twouser,name,sid,body);
		}
		else if("selectIN".equals(action))
		{
			return postsAcService.getPostsAcInfoList(user,floor,name,sid);
		}
		return null;
	}
	
}
