package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TeacherList implements DBQueryInterface {
	List<Teacher> listOfTeachers = new ArrayList<Teacher>();

	@Override
	public void getListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub

	}

}
