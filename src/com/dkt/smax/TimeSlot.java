package com.dkt.smax;

public class TimeSlot {
	Subject subject; // one of the subjects is recess
	Teacher teacher;
	int room;
	int duration; // in minutes
	Time startTime;
	public enum SlotTypes {SUBJECT, RECESS, PERSONAL_SHORT, PERSONAL_LONG}
	public TimeSlot(Subject subject, Time startTime, int duration) {
		super();
		this.subject = subject;
		this.duration = duration;
		this.startTime = startTime;
	};
	
//	Room room;
}
