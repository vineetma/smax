package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.dkt.StudentTimeTable.DBQueryInterface;
import com.dkt.StudentTimeTable.Teacher;

public class TeacherList implements DBQueryInterface {
	List<Teacher> listOfTeachers = new ArrayList<Teacher>();

	@Override
	public void getListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub

	}

}
