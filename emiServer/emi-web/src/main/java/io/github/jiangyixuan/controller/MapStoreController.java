package io.github.jiangyixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jiangyixuan.entity.StoreMap;
import io.github.jiangyixuan.service.MapStoreService;

@Controller
public class MapStoreController {

	@Autowired
	private MapStoreService mapStoreService;
	
	@RequestMapping(value="/getMapStores",method=RequestMethod.POST)
	@ResponseBody
	public List<StoreMap> getMapStores(String flag,String value)
	{
		return mapStoreService.getMapStores(flag, value);
	}
	
}
