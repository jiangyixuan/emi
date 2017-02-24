package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.AllStoreOverview;
import io.github.jiangyixuan.service.AllStoreService;

@Controller
public class AllStoreOverviewController {

	@Autowired
	private AllStoreService allStoreService;
	
	@RequestMapping(value= "/getAllStoreOverviews",method=RequestMethod.POST)
	@ResponseBody
	public List<AllStoreOverview> getAllStoreOverviews()
	{
		return allStoreService.getAllStoreOverviews();
	}
	
}
