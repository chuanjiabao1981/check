package com.check.v3.domain.util;

import java.util.UUID;

import org.joda.time.DateTime;

import com.check.v3.domain.CheckImage;

public class CheckImageUtil {

	public static String BuildImageName(CheckImage i)
	{
		DateTime s = new DateTime();
		return s.toString("yyyy-MM-dd")+"/"+i.getClass().getSimpleName()+"/"+UUID.randomUUID();
	}

}
