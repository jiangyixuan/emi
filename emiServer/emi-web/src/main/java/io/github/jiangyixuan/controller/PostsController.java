package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.Posts;
import io.github.jiangyixuan.service.PostsService;

@Controller
public class PostsController {

	@Autowired
	private PostsService postsService;
	
	@RequestMapping(value = "/postsCl",method=RequestMethod.POST)
	@ResponseBody
	public Object postsCl(String action,String name,String title,
			String body,String time,String price,String address,String sid)
	{
		
		if("select".equals(action))
		{
			List<Posts> postsList = postsService.getPostsList();
			return postsList;
		}
		else if("insert".equals(action))
		{
			boolean result = postsService.insertPostsOne(name, title, body, time, price, address);
			return result;
		}
		else if("delete".equals(action))
		{
			boolean result = postsService.deletePostsOne(sid,name, title);
			return result;
		}
		return null;
	}
	
}
