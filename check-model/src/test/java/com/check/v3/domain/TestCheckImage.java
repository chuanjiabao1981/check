package com.check.v3.domain;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:application-model.xml"})
public class TestCheckImage {

	@Resource(name="imagePathPrefix")
	String testMe;
	@Test
	public void testGetImageName()
	{
		
		System.err.println(testMe);
		CheckImage checkImage  = new CheckImage();
		System.err.println(checkImage.getTestMe());
	}
}
