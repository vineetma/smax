package com.dkt.StudentTimeTable;

import java.sql.Connection;

public class Admin extends Person  {

	public Admin(int id, String firstName, String lastName, String email,
			String Password) {
		super(id, firstName, lastName, email, Password);
		// TODO Auto-generated constructor stub
	}
	public Admin(String em) {
		this(0, "", "", em, "");
	}
	@Override
	public boolean getObjectFromDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveObjectToDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObjectToDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public USER_ROLE getPersonType() {
		// TODO Auto-generated method stub
		return USER_ROLE.ADMIN;
	}

}
