package com.dkt.smax;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
	List <Subject> subjects;
	public int getPersonType() { return 2; }
	public List<Subject> getSubjects() {
		return subjects;
	}
	public Teacher(String name) {
		super(name);
		this.subjects = new ArrayList<Subject>();
	}
	public boolean addSubject(Subject s) {
		this.subjects.add(s);
		return true;
	}
}
