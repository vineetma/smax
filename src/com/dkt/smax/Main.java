package com.dkt.smax;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.sun.org.apache.xpath.internal.operations.Equals;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(Main.testTime())
			System.out.println("Test for class Time: Passed");
		else
			System.out.println("Test for class Time: Failed");
	}
	/* All test cases for the classes */

	/* Class: Time */
	public static boolean testTime() {
		Time tm = new Time(9, 15);
		Time tm1 = tm.add(new Time(0, 180));
//		if(EqualsBuilder.reflectionEquals(tm1, new Time(12,15))) {
		if(tm1.compareTo(new Time(12,16))) {
			System.out.println("Time: Test: Addition: Passed");
		} else {
			System.out.println("Time: Test: Addition: Failed");
			return false;
		}
		
		Time tm2 = tm.subtract(new Time(0, 200));
		if(EqualsBuilder.reflectionEquals(tm2, new Time(5,55))) {
			System.out.println("Time: Test: Subtraction: Passed");
		} else {
			System.out.println("Time: Test: Subtraction: Failed");
			return false;
		}
		return true;
	}

	/* Class: TimeSlot */
	public static boolean testTimeSlot() {
		TimeSlot ts = new TimeSlot(new Subject(), new Time(8,0), 40);		
		return false;
	}
}
