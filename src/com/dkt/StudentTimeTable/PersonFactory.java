package com.dkt.StudentTimeTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonFactory {
	static StudentDatabase stdb = null;
	public static void setDB(StudentDatabase sd) {
		stdb = sd;
	}
	public static Person getPersonFromEmail(String em) throws ProvisionException {
		java.sql.PreparedStatement pStmt;
		if(stdb != null) {
			try {
			pStmt = stdb.conn.prepareStatement("select * from st_users where stu_email=?");
			pStmt.setString(1, em);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				Person person = null;
				int role = rs.getInt("stu_role");
				if(role == 1) {
					person = new Admin(em);
				} else if(role == 2) {
					person = new Student(em);
				} else if(role == 3) {
					person = new Teacher(em);
				}
				stdb.readObject(person);
				return person;
			} else {
				throw(new ProvisionException(6, "User with this email-id does not exist"));
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
			
		}
		return null;
	}
}
