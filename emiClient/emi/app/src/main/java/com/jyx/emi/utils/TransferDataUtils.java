package com.jyx.emi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TransferDataUtils {
	
	public static final String PREF_NAME="data";
	
	public static String getString(String key,String defaultData,Context context)
	{
		SharedPreferences sp=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
		return sp.getString(key,defaultData);
	}
	public static void setString(String key,String data,Context context)
	{
		SharedPreferences sp=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, data).commit();
	}

	public static void remove(String key,Context context){
		SharedPreferences sp=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

}
