package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.CommOverview;
import io.github.jiangyixuan.service.CommOverviewService;

@Controller
public class CommOverviewController {

	private static final int PAGE_COUNT=4;
	
	@Autowired
	private CommOverviewService commOverviewService;
	
	@RequestMapping(value = "/getCommOverviews",method=RequestMethod.POST)
	@ResponseBody
	public List<CommOverview> getCommOverviews(String id,String page,String sortby)
	{
		String top = Integer.parseInt(page)*PAGE_COUNT+"";
		return commOverviewService.getCommOverviews(id, top, sortby);
	}
	
}
