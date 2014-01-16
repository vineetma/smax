package com.dkt.smax;

import java.util.List;

public class WeekSchedule {
	protected Time dayStartingTime;
	protected int weekNumber;
	protected int duration; // in minutes
	protected List<DaySchedule> daySchedules;
	public boolean addDaySchedule(int dn, //day number 
								DaySchedule ds) {
		return true;
	}
}
