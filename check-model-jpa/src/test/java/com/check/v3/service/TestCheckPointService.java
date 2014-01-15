package com.check.v3.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.CheckImage;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-model.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestCheckPointService {

	@Autowired
	private CheckPointService checkPointService;
	
	@Test
	public void testTemp()
	{
		checkPointService.findById(1L);
		CheckImage checkImage = new CheckImage();
		System.err.println("++++"+checkImage.getTestMe());
	}

}
