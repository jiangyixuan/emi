package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.github.jiangyixuan.entity.CommCollect;
import io.github.jiangyixuan.entity.CommOverview;
import io.github.jiangyixuan.service.CommOverviewService;

@Controller
public class CollectController {

	@Autowired
	private CommOverviewService commOverviewService;
	
	@RequestMapping(value= "/getCollect",method=RequestMethod.POST)
	@ResponseBody
	public List<CommOverview> getCollect(String attribute)
	{
		List<CommCollect> commCollects = new Gson().fromJson(attribute, new TypeToken<List<CommCollect>>(){}.getType());
	
		return commOverviewService.getCollect(commCollects);
	}
	
}
