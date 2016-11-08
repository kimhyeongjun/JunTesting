package com.pro.jun.junit;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalcuratorTest {
	@Test
	public void testSum() {
		Calcurator c = new Calcurator();
		double result = c.sum(10, 50);
		assertEquals(60, result, 0);
	}
}
