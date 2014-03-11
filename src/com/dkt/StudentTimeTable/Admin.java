package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		java.sql.PreparedStatement pStmt;
		try {
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement("select * from st_student join st_users on st_id=stu_id where stu_email=?");
			pStmt.setString(1, emailId);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				this.fName = rs.getString("stu_fname");
				this.lName = rs.getString("stu_lname");
				//email id already there
				//this.emailId = rs.getString("stu_email");
				this.password= rs.getString("stu_password");
				this.role = rs.getInt("stu_role");
				this.Id = rs.getInt("st_id");
			} else {
				throw(new ProvisionException(6, "Administrator with this email-id does not exist"));
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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


}
