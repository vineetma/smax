package com.dkt.smax;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
	protected String branch;
	protected int semester;
	protected int section;
	protected int rollno;

	protected String getBranch() {
		return branch;
	}

	protected void setBranch(String branch) {
		this.branch = branch;
	}

	protected int getSemester() {
		return semester;
	}

	protected void setSemester(int sem) {
		this.semester = sem;
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

	public int getPersonType() {
		return 1;
	}

	public TimeTable getTimeTable() {
		TimeTable t1 = new TimeTable();
		return t1;
	}
	public List<Student> getStudentlist(){
List<Student> t2 = new ArrayList<Student>();
return t2;

	}

	public Student(String name, int rollno, int sec, int section, String branch) {
		super(name);
		this.rollno = rollno;
		this.section = section;
		this.branch = branch;
	}
}
