package com.dkt.smax.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dkt.StudentTimeTable.TimeDuration;

public class TimeTest {

	@Test
	public void testGetHour() {
//		fail("Not yet implemented"); // TODO
		TimeDuration testTime = new TimeDuration(2,3);
		assertEquals(2, testTime.getHour());
	}

	@Test
	public void testSetHour() {
		TimeDuration testTime = new TimeDuration(2,3);
		testTime.setHour(20);
		assertEquals(20, testTime.getHour());
	}

	@Test
	public void testGetMinutes() {
		TimeDuration testTime = new TimeDuration(2,3);
		assertEquals(3, testTime.getMinutes());
	}

	@Test
	public void testSetMinutes() {
		TimeDuration testTime = new TimeDuration(2,3);
		testTime.setMinutes(30);
		assertEquals(30, testTime.getMinutes());
		testTime.setMinutes(130);
		assertEquals(10, testTime.getMinutes());
		assertEquals(4, testTime.getHour());
	}


	@Test
	public void testAdd() {
		TimeDuration tm = new TimeDuration(9, 15);
		TimeDuration tm1 = tm.add(new TimeDuration(0, 180));
		TimeDuration result = new TimeDuration(12, 15);
		assertEquals(result.getHour(), tm1.getHour());
		assertEquals(result.getMinutes(), tm1.getMinutes());

		tm1 = tm.add(new TimeDuration(0, 160));
		result = new TimeDuration(11, 55);
		assertEquals(result.getHour(), tm1.getHour());
		assertEquals(result.getMinutes(), tm1.getMinutes());

		tm1 = tm.add(new TimeDuration(20, 160));
		result = new TimeDuration(31, 55);
		assertEquals(result.getHour(), tm1.getHour());
		assertEquals(result.getMinutes(), tm1.getMinutes());
		
		tm1 = tm.add(new TimeDuration(0, 1750));
		result = new TimeDuration(38, 25);
		assertEquals(result.getHour(), tm1.getHour());
		assertEquals(result.getMinutes(), tm1.getMinutes());
		
	}

	@Test
	public void testGetDays() {
		TimeDuration td = new TimeDuration(48, 20);
		assertEquals(2, td.getDays());
	}

	@Test
	public void testSubtract() {
		TimeDuration td = new TimeDuration(25, 20);
		TimeDuration ot = new TimeDuration(0, 180);
		TimeDuration diff = td.subtract(ot);
		assertEquals(22, diff.getHour());
		assertEquals(20, diff.getMinutes());
		
		ot = new TimeDuration(0, 185);
		diff = td.subtract(ot);
		assertEquals(22, diff.getHour());
		assertEquals(15, diff.getMinutes());
		
		ot = new TimeDuration(0, 210);
		diff = td.subtract(ot);
		assertEquals(21, diff.getHour());
		assertEquals(50, diff.getMinutes());
		
	}

	@Test
	public void testCompareTo() {
		TimeDuration td = new TimeDuration(25, 20);
		TimeDuration ot = new TimeDuration(0, 190);
		
		assertEquals(false, td.compareTo(ot));
		TimeDuration n = new TimeDuration(21, 70);
		System.out.println(n);
		System.out.println(ot.add(n));
		assertEquals(true, td.compareTo(ot.add(n)));
	}

}
