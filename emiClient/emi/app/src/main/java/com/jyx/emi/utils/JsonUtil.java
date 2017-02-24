package com.jyx.emi.utils;


import com.google.gson.Gson;

public class JsonUtil {

	private static Gson gson=null;
	
	public static String createJsonString(Object value)
	{
		if(gson==null)
		{
			gson=new Gson();
		}
		String gsonString=gson.toJson(value);
		
		return gsonString;
	}

}
