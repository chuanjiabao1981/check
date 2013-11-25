package com.check.v3.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestQuickReport {
	
	@Test
	public void testEquals()
	{
		QuickReport s = new QuickReport();
		assertTrue(s.equals(s));
		QuickReport p = new QuickReport();
		assertFalse(s.equals(p));
		s.setId(1L);
		assertFalse(s.equals(p));
		p.setId(1L);
		assertTrue(s.equals(p));

	
	}

}
