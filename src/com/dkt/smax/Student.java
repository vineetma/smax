package com.dkt.smax;

public class Student extends Person {

protected String getBranch() {
		return branch;
	}
	protected void setBranch(String branch) {
		this.branch = branch;
	}
	protected int getSem() {
		return sem;
	}
	protected void setSem(int sem) {
		this.sem = sem;
	}
	protected int getSection() {
		return section;
	}
	protected void setSection(int section) {
		this.section = section;
	}
	protected int getRollno() {
		return rollno;
	}
	protected void setRollno(int rollno) {
		this.rollno = rollno;
	}
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
}

	

