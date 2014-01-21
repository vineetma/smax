package com.dkt.smax;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
	List <Subject> subjects;
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	public int getPersonType() { return 2; }
	
	
	public Teacher(String name) {
		super(name);
		this.subjects = new ArrayList<Subject>();
	}
	public boolean addSubject(Subject s) {
		this.subjects.add(s);
		return true;
	}
}
