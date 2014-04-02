package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dkt.StudentTimeTable.DBInterface;
import com.dkt.StudentTimeTable.Person;
import com.dkt.StudentTimeTable.Subject;

public class Teacher extends Person implements DBInterface {

	List<Subject> subjects;
	 protected int teacherId;
	 protected int department;
	 
	 
	 public String getFName(){
		 return super.getfName();
	 }
	 public String getLName(){
		 return super.getlName();
	 }
	 public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public Teacher(String fn, String ln, String email,String password, int dpt,int tid) {
			super(fn, ln, email);
			
			this.department= dpt;
			this.teacherId = tid;
			this.role = 3;
			this.password=password;
		}
	public Teacher(String email ){
		this("","",email,"",0,0);
		
	}
	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
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

	public USER_ROLE getPersonType() {
		return USER_ROLE.TEACHER;
	}

	@Override
	public boolean getObjectFromDatabase(Connection conn) throws ProvisionException {
		java.sql.PreparedStatement pStmt;
		try {
			pStmt = conn.prepareStatement("select * from st_teacher join st_users on stt_id=stu_id where stu_email=?");
			pStmt.setString(1, emailId);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				this.fName = rs.getString("stu_fname");
				this.lName = rs.getString("stu_lname");
				this.role = rs.getInt("stu_role");
				if(this.role != 3) throw new ProvisionException(3, "Not a Teacher");
				this.Id = rs.getInt("stt_id");
				
				this.password = rs.getString("stu_password");
				this.teacherId = rs.getInt("stt_teacher_id"); 
				this.department = rs.getInt("stt_deptt");
				
				System.out.println("Deptt: " + this.department  );
				
				return true;
			} else {
				throw(new ProvisionException(10, "Teacher with email-id does not exist"));
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
			java.sql.PreparedStatement pstmt = null;
			if (this.getId() == 0) {
				pstmt = conn.prepareStatement ("select stt_teacher_id from st_teacher where stt_teacher_id='"
						+ teacherId + "'");
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
				ResultSet keys = pstmt.getGeneratedKeys();
				keys.next();
				int id = keys.getInt(1);
				if (id != 0) {
					this.setId(id);
					java.sql.PreparedStatement pStmt = conn.prepareStatement("insert into st_teacher (stt_id, stt_teacher_id, stt_deptt) values (?, ?, ?)");
					pStmt.setInt(1, id);
					pStmt.setInt(2,  this.teacherId);
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
				pStmt = conn.prepareStatement("update st_users set stu_fname=?, stu_lname=?, stu_email=?,stu_role=? where stu_id=?");
				pStmt.setString(1, this.fName);
				pStmt.setString(2, this.lName);
				pStmt.setString(3, this.emailId);
				pStmt.setInt(4, this.role);
				pStmt.setInt(5, this.getId());
				pStmt.executeUpdate();
				
				pStmt = conn.prepareStatement("update st_teacher set stt_teacher_id=?, stt_deptt=? where stt_id=?");
				pStmt.setInt(1, this.teacherId);
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
