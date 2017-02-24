package io.github.jiangyixuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.CommDetail;
import io.github.jiangyixuan.service.CommDetailService;

@Controller
public class CommDetailController {

	@Autowired
	private CommDetailService commDetailService;
	
	@RequestMapping(value="/getCommDetail",method=RequestMethod.POST)
	@ResponseBody
	public CommDetail getCommDetail(String commId)
	{
		CommDetail commDetail = commDetailService.getCommDetail(commId);
		return commDetail;
	}
	
	
	
}
