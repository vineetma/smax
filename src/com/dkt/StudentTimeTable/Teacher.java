package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Teacher extends Person implements DBInterface {

	List<Subject> subjects;
	 protected String teacherId;
	 protected int department;
	 
	 public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public Teacher(String fn, String ln, String email,int dpt,String tid) {
			super(fn, ln, email);
			
			this.department= dpt;
			this.teacherId = tid;
		}
	public Teacher(String teacherId){
		this("","","",0,teacherId);
		
	}
	public String getTecherId() {
		return teacherId;
	}

	public void setTecherId(String teacherId) {
		this.teacherId = teacherId;
	}

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
	public boolean getObjectFromDatabase(Connection conn) throws ProvisionException {
		java.sql.PreparedStatement pStmt;
		try {
			pStmt = conn.prepareStatement("select * from st_teacher join st_users on stt_id=stu_id where stt_teacher_id=?");
			pStmt.setString(1, teacherId);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				this.fName = rs.getString("stu_fname");
				this.lName = rs.getString("stu_lname");
				this.Id = rs.getInt("stt_id");
				this.emailId = rs.getString("stu_email"); 
				this.department = rs.getInt("stt_deptt");
				
				System.out.println("Deptt: " + this.department  );
				
				return true;
			} else {
				throw(new ProvisionException(10, "Teacher with Teacher-id does not exist"));
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
		// TODO Auto-generated method stub

	}


	@Override
	public boolean saveObjectToDatabase(Connection conn) throws ProvisionException {
		java.sql.Statement stmt;
		ResultSet rs;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			if (this.getId() == 0) {
				sql = "select stt_teacher_id from st_teacher where stt_teacher_id='"
						+ teacherId + "'";
				rs = stmt.executeQuery(sql); 
				if (rs.next()) {
					// TODO: create error json object and then return
					throw new ProvisionException(2, "User already exists");
				}
				sql = "insert into st_users (stu_fname, stu_lname, stu_email) "
						+ " values('" + this.fName + "', '" + this.lName
						+ "', '" + this.emailId + "'" + ")";
				stmt.executeUpdate(sql);
				ResultSet keys = stmt.getGeneratedKeys();
				keys.next();
				int id = keys.getInt(1);
				if (id != 0) {
					this.setId(id);
					java.sql.PreparedStatement pStmt = conn.prepareStatement("insert into st_teacher (stt_id, stt_teacher_id, stt_deptt) values (?, ?, ?)");
					pStmt.setInt(1, id);
					pStmt.setString(2,  this.teacherId);
					pStmt.setInt(3, this.department);
					
					pStmt.executeUpdate();
				}
				conn.commit();
				return true;
				

			} else {
				conn.setAutoCommit(false);
				java.sql.PreparedStatement pStmt = conn.prepareStatement("select stu_id from st_users where stu_id=?");
				pStmt.setInt(1, this.getId());
				rs = pStmt.executeQuery();
				if (rs.next() == false) {
					// TODO: create error json object and then return
					throw new ProvisionException(3, "User does not exist. Invalid Id");
				}
				pStmt = conn.prepareStatement("update st_users set stu_fname=?, stu_lname=?, stu_email=? where stu_id=?");
				pStmt.setString(1, this.fName);
				pStmt.setString(2, this.lName);
				pStmt.setString(3, this.emailId);
				pStmt.setInt(4, this.getId());
				pStmt.executeUpdate();
				
				pStmt = conn.prepareStatement("update st_teacher set stt_teacher_id=?, stt_deptt=? where stt_id=?");
				pStmt.setString(1, this.teacherId);
				pStmt.setInt(2, this.department);
				
				pStmt.setInt(3, this.getId());
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
	public boolean deleteObjectToDatabase(Connection conn) {
		// TODO Auto-generated method stub
		return true;
	}

}
