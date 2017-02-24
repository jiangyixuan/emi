package io.github.jiangyixuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.service.MapStoreService;

@Controller
public class LanLngController {

	@Autowired
	private MapStoreService mapStoreService;
	
	@RequestMapping(value= "/getStoreLanLag",method=RequestMethod.POST)
	@ResponseBody
	public String getStoreLanLag(String storeId)
	{
		return mapStoreService.getStoreLanLag(storeId);	
	}
	
}
