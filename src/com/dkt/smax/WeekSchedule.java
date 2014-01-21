package com.dkt.smax;

import java.util.ArrayList;
import java.util.List;

public class WeekSchedule {
	protected TimeDuration dayStartingTime;
	protected int weekNumber;
	protected int duration; // in minutes
	protected List<DaySchedule> daySchedules;
	protected int day; // Vineet: What is this variable for??
	public int getDay() { //shouldn't this be daySchedule??
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public WeekSchedule() {
		daySchedules = new ArrayList<DaySchedule>();
	}
	public WeekSchedule(DaySchedule day) {
		daySchedules = new ArrayList<DaySchedule>();
		daySchedules.add(day);
	}
	public WeekSchedule(int day){//this constructor is also not required
		this.day=day;
	}
	
	public boolean addDaySchedule(int dn, //day number 
			DaySchedule ds) {
		return true;
	}
	public WeekSchedule add(DaySchedule d){ //checkout prototype listed above
		daySchedules.add(d);
//		newday.day+= d.day;
		return this   ;
	}
}