package com.dkt.smax;
/**
 * Time stores the value of duration or relative time which has no bounds in hours
 * It may be referred as duration also. This is a datatype that is used to store 
 * the hours and minutes which can be added and subtracted from each other.
 * 
 * @author Vineet Kumar Maheshwari
 *
 */
class Time {
	// Attributes
	protected int hour; //hour of the Time / duration to be maintained in this object
	protected int minutes; // minutes of the Time/Duration to be maintained in this object
    protected int week; // I think we should keep Time independent of week or day details
    
    /**
     * returns the hour attribute of current object
     * @return returns the hour attribute of current object
     */
	public int getHour() {
		return hour;
	}
	
	/**
	 * sets the hour attribute of the current Time object
	 * 
	 * @param hour value of hour that is to be set in the current object
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * returns the value of minutes stored in current object
	 * 
	 * @return value of current object minutes attribute
	 */
	public int getMinutes() {
		return minutes;
	}
	
	/**
	 * Set minutes parameter of Time object
	 * 
	 * @param minutes value of minutes to be saved
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	/**
	 * Helps to assign value of another Time object to newly created
	 * Object
	 * 
	 * @param t another object, values of which are to be copied
	 */
	public Time(Time t) {
		this.hour = t.hour;
		this.minutes = t.minutes;
	}
	public Time(int hour, int minutes) {
		this.hour = hour;
		this.minutes = minutes;
	}
/*	public Time(week w){
		this.week =w.week;
	}
	public Time(int week){
		this.week = week;
	}
	public Time add(week t){
		Time newweek = new week(this);
		newweek.week += w.week;
	     return newweek;
		
	}*/
	/**
	 * Adds the value of time to the current time object. Addition takes place
	 * at minutes and hour level. Value of hours is not limited to the value
	 * of 24. 
	 * 
	 * @param t Value of another duration object to be added to this object
	 * @return
	 */
	public Time add(Time t) {
		Time newTime = new Time(this);
		newTime.minutes += t.minutes;
		newTime.hour += t.hour + newTime.minutes / 60;
		newTime.minutes = newTime.minutes % 60;
		return newTime;
	}
	
	/**
	 * Returns the number of days contained in the value of Time
	 * 
	 * @return
	 */
	public int getDays() {
		return this.hour/24;
	}
	
	/**
	 * Subtracts time value from this object. Negative value of time, which is
	 * either negative hour value is not allowed. Such an attempt will return
	 * null value
	 * TODO: Throw an exception in case the value is less than zero 
	 * @param t
	 * @return
	 */
	public Time subtract(Time t) {
		Time newTime = new Time(this);
		newTime.minutes -= t.minutes;
		newTime.hour -= t.hour;
		if(newTime.hour < 0) return null;
		if(newTime.minutes < 0) {
			newTime.hour += newTime.minutes/60 - 1;
			if(newTime.hour < 0)return null;
			newTime.minutes = 60 + newTime.minutes%60; 
		}
		return newTime;
	}
	/**
	 * Returns true if both value of argument is equal to the value of current
	 * object.
	 * TODO: Should return 0 for object values being equal, 1 for argument being
	 *       greater than the current object, -1 for argument being less than
	 *       the current object.
	 *       
	 * @param t another time object with which this object is to be compared
	 *          for value
	 * @return
	 */
	public boolean compareTo (Time t) {
		if(this.hour == t.getHour() && this.minutes == t.getMinutes())
			return true;
		else return false;
	}
	/*
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}*/
}
