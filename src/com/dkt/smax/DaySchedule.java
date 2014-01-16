package com.dkt.smax;

import java.util.List;

public class DaySchedule {
	enum enumTypeOfDay {HOLIDAY, STUDYDAY};
	enum enumDayName {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};
	
	enumDayName dayName;
	enumTypeOfDay tod;
	
	enumTypeOfDay getTypeOfDay() {
		return this.tod;
	}
	enumDayName getNameOfDay() {
		return this.dayName;
	}
	
	protected List<TimeSlot> timeSlots;
	public DaySchedule() {
		this.tod = DaySchedule.enumTypeOfDay.STUDYDAY;
	}
	public DaySchedule(enumDayName dn, enumTypeOfDay td){
		this.dayName =dn;
		this.tod = td;
	}
}
