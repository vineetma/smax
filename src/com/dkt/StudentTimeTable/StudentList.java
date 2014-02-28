package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentList implements DBQueryInterface {
	List<Student> listOfStudents = new ArrayList<Student>();

	@Override
	public void getListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub

	}

}
