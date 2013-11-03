package com.check.v3.util;

public  class  KeyUtils {
	
	public static String buildKey(String controller,String action)
	{
		return controller+":"+action;
	}

}
