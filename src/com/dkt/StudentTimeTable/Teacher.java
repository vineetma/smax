package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.util.List;

public class Teacher extends Person implements DBInterface {

	List<Subject> subjects;

	public boolean addSubject(Subject s) {
		this.subjects.add(s);
		return true;
	}

	public Teacher(int id, String firstName, String lastName, String email,
			String Password) {
		super(id, firstName, lastName, email, Password);

	}

	public int getPersonType() {
		return 2;
	}

	@Override
	public boolean getObjectFromDatabase(Connection conn) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean saveObjectToDatabase(Connection conn) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteObjectToDatabase(Connection conn) {
		// TODO Auto-generated method stub
		return true;
	}

}
