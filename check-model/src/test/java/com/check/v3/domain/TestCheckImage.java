package com.check.v3.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestCheckImage {

	@Test
	public void testGetImageName()
	{
		CheckImage i = new QuickReportImage();
		System.err.println(i.getName());
		assertNotNull(i.getName());
	}
}
