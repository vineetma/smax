package com.dkt.StudentTimeTable;

public class SubjectTeacher {
	protected int id;
	protected Subject subject;
	protected Teacher teacher;
	public SubjectTeacher(int id, Subject s, Teacher t) {
		
		this.id = id;
		this.subject = s;
		this.teacher = t;
	}
	public SubjectTeacher(int id, int tId, String fn, String ln, int sId, String sn){
		this.subject = new Subject(sId, sn);
		this.teacher = new Teacher(tId, fn, ln, null, null);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Subject getSubject() {
		return this.subject;
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
	
	

}
