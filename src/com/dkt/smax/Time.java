package com.dkt.smax;

class Time {
	// Attributes
	protected int hour;
	protected int minutes;
     protected int week;
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public Time(Time t) {
		this.hour = t.hour;
		this.minutes = t.minutes;
	}
	public Time(int hour, int minutes) {
		this.hour = hour;
		this.minutes = minutes;
	}
	public Time add(Time t) {
		Time newTime = new Time(this);
		newTime.minutes += t.minutes;
		newTime.hour += t.hour + newTime.minutes / 60;
		newTime.minutes = newTime.minutes % 60;
		return newTime;
	}
	public Time subtract(Time t) {
		Time newTime = new Time(this);
		newTime.minutes -= t.minutes;
		newTime.hour -= t.hour;
		if(newTime.minutes < 0) {
			newTime.hour += newTime.minutes/60 - 1;
			if(newTime.hour < 0)return null;
			newTime.minutes = 60 + newTime.minutes%60; 
		}
		return newTime;
	}
	public boolean compareTo (Time t) {
		if(this.hour == t.getHour() && this.minutes == t.getMinutes())
			return true;
		else return false;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public Time(week w){
		this.week =w.week;
	}
	public Time(int week){
		this.week = week;
	}
	public Time add(week t){
		Time newweek = new week(this);
		newweek.week += w.week;
	     return newweek;
		
	}
}
