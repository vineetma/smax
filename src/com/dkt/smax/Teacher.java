package com.dkt.smax;

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
}
