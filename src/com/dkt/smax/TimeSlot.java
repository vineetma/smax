package com.dkt.smax;

public class TimeSlot {
	Subject subject; // one of the subjects is recess
	Teacher teacher;
	int room;
	int timeSlotNumber;
	public enum SlotTypes {SUBJECT, RECESS, PERSONAL_SHORT, PERSONAL_LONG}
	public TimeSlot(int tsn, Subject subject) {
		super();
		this.subject = subject;
		this.timeSlotNumber = tsn;
	};
	
//	Room room;
}
