package com.jyx.emi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference封装
 */
public class PreferencesUtils {

	public static final String PREF_NAME="config";
	
	public static boolean getBoolean(String key,boolean defaultValue,Context context)
	{
		SharedPreferences sp=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}
	
	public static void setBoolean(String key,boolean value,Context context)
	{
		SharedPreferences sp=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

}
