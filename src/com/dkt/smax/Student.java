package com.dkt.smax;

public class Student extends Person {
	protected String branch; 
	protected int sem;
	protected int section;
	protected int rollno;	
	public int getPersonType() { return 1; }
	public Student(String name, int rollno, int sec, int section, String branch) {
		super(name);
		this.rollno = rollno;
		this.section = section;
		this.branch = branch;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
}
