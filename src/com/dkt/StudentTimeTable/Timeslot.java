package com.dkt.StudentTimeTable;

import java.util.Date;

public class Timeslot {  
	protected int id;
	protected int number;
	protected int subjectTeacherId;
	protected SubjectTeacher subjectTeacher;
	protected int room;
	protected int day;
	protected int week;
	protected Date slotDate;
	
	public Date getSlotDate() {
		return slotDate;
	}
	public void setSlotDate(Date slotDate) {
		this.slotDate = slotDate;
	}
	public Timeslot(Date slotDate,int i, int wk, int d,int num, SubjectTeacher st, int r) {
		this.slotDate = slotDate; this.id = i; this.number = num; this.subjectTeacher = st; this.room = r; this.day = d; this.week = wk;
	}
	public Timeslot(Date slotDate,int i, int wk, int d, int num, int st, int r){
		this.id = i; this.number = num; this.subjectTeacherId = st; this.room = r; this.day = d; this.week = wk;
	}
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSubjectTeacherId() {
		return subjectTeacherId;
	}

	public void setSubjectTeacherId(int subjectTeacherId) {
		this.subjectTeacherId = subjectTeacherId;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public SubjectTeacher getSubjectTeacher() {
		return subjectTeacher;
	}
	public void setSubjectTeacher(SubjectTeacher subjectTeacher) {
		this.subjectTeacher = subjectTeacher;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
