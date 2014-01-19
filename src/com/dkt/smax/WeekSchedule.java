package com.dkt.smax;

import java.util.List;

public class WeekSchedule {
	protected Time dayStartingTime;
	protected int duration; // in minutes
	protected List<DaySchedule> daySchedules;
	protected int day;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public WeekSchedule(day d){
		this.day=d.day;
	}
	public WeekSchedule(int day){
		this.day = day;
	public WeekSchedule add(day d){
		WeekSchedule newday= new day(this);
		newday.day+= d.day;
		return newday   ;
		
		
	}
}
}
   