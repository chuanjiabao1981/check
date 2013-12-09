package com.check.v3.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestQuickReportResolve {
	
	@Test
	public void testEquals()
	{
		QuickReportResolve a = new QuickReportResolve();
		QuickReportResolve b = a;
		assertTrue(a.equals(b));
		b = new QuickReportResolve();
		assertFalse(a.equals(b));
		a.setId(1L);
		b.setId(1L);
		assertTrue(a.equals(b));
		a.setId(2L);
		assertFalse(a.equals(b));

	}

}
