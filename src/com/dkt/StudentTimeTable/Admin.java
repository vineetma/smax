package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dkt.StudentTimeTable.Person;

public class Admin extends Person  {
	
	
	
	
	public Admin(String fn, String ln, String email,String password) {
		super(fn, ln, email);
		this.password=password;
		this.role=1;
	}
	public Admin(String em) {
		this( "", "", em, "");
	}

	public Admin(int id, String firstName, String lastName, String email,
			String Password) {
		super(id, firstName, lastName, email, Password);
		
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean getObjectFromDatabase(Connection conn)
			throws ProvisionException {
		java.sql.PreparedStatement pStmt;
		try {
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement("select * from st_users  where stu_email=?");
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
		java.sql.Statement stmt;
		ResultSet rs;
		try {
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			String sql;
			java.sql.PreparedStatement pstmt = null;
			if (this.getId() == 0) {
				pstmt = conn.prepareStatement("select stu_email from st_users where stu_email=?");
				pstmt.setString(1, emailId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					// TODO: create error json object and then return
					throw new ProvisionException(2, "User already exists");
				}
				pstmt = conn.prepareStatement("insert into st_users (stu_fname, stu_lname, stu_email , stu_password, stu_role) "
						+ " values(?, ?, ?, ?, ?)");
				pstmt.setString(1, this.fName);
				pstmt.setString(2, this.lName);
				pstmt.setString(3, this.emailId);
				pstmt.setString(4, this.password);
				pstmt.setInt(5, this.role);
				
				pstmt.executeUpdate();
				
				return true;
				

			} else {
				java.sql.PreparedStatement pStmt = conn.prepareStatement("select stu_id from st_users where stu_id=?");
				pStmt.setInt(1, this.getId());
				rs = pStmt.executeQuery();
				if (rs.next() == false) {
					// TODO: create error json object and then return
					throw new ProvisionException(3, "User does not exist. Invalid Id");
				}
				pStmt = conn.prepareStatement("update st_users set stu_fname=?, stu_lname=?, stu_email=?, stu_password=?, stu_role=? where stu_id=?");
				pStmt.setString(1, this.fName);
				pStmt.setString(2, this.lName);
				pStmt.setString(3, this.emailId);
				pStmt.setString(4, this.password);
				pStmt.setInt(5, this.role);
				pStmt.setInt(6, this.getId());
				pStmt.executeUpdate();
				
				
				conn.commit();
				return true;	
				
			}
			
			} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
				throw new ProvisionException(4, "DB Error, while working with Student DB");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ProvisionException(4, "DB Error, Could not roll back");
			}
		}

	}

	@Override
	public boolean deleteObjectToDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		return false;
	}


}
