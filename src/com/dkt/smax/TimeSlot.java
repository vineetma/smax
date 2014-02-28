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
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public int getTimeSlotNumber() {
		return timeSlotNumber;
	}
	public void setTimeSlotNumber(int timeSlotNumber) {
		this.timeSlotNumber = timeSlotNumber;
	};
	
//	Room room;
}
