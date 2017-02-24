package io.github.jiangyixuan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.SmallSort;
import io.github.jiangyixuan.service.SortDataService;

@Controller
public class SortDataController {

	@Autowired
	private SortDataService sortDataService;
	
	@RequestMapping("/getSortData")
	@ResponseBody
	public Map<String, List<SmallSort>> getSortData()
	{
		return sortDataService.getSortData();
	}
	
}
