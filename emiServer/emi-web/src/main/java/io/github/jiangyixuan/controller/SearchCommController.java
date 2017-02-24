package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.SearchComm;
import io.github.jiangyixuan.service.SearchCommService;

@Controller
public class SearchCommController {

	@Autowired
	private SearchCommService searchCommService;
	
	@RequestMapping(value="/getSearchComm",method=RequestMethod.POST)
	@ResponseBody
	public List<SearchComm> getSearchComm(String keyword)
	{
		return searchCommService.getSearchComm(keyword);
	}
	
}
