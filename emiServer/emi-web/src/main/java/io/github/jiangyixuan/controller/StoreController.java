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
public class StoreController {
	
	@Autowired
	private CommOverviewService commOverviewService;
	
	@RequestMapping(value="/getStoreCommOverviews",method=RequestMethod.POST)
	@ResponseBody
	public List<CommOverview> getStoreCommOverviews(String storeId)
	{
		return commOverviewService.getStoreCommOverviews(storeId);
	}

}
