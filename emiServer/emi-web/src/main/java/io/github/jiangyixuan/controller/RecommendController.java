package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.RecommendStoreOverview;
import io.github.jiangyixuan.service.RecommendStoreService;

@Controller
public class RecommendController {

	@Autowired
	private RecommendStoreService recommendStoreService;
	
	@RequestMapping(value = "/getRecommendStoreOverviews",method=RequestMethod.POST)
	@ResponseBody
	public List<RecommendStoreOverview> getRecommendStoreOverviews(String flag,String smallSortId)
	{
		return recommendStoreService.getStoreOverviews(flag, smallSortId);
	}
	
}
