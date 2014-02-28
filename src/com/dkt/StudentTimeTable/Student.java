package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class Student extends Person implements DBInterface {

	protected int department;
	protected int semester;
	protected int section;
	protected String rollNo;

	public Student(String fn, String ln, String email, int dpt, int sem,
			int sec, String rn) {
		super(fn, ln, email);
		this.department = dpt;
		this.semester = sem;
		this.section = sec;
		this.rollNo = rn;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public Student(int id, String firstName, String lastName, String email,
			String Password) {
		super(id, firstName, lastName, email, Password);

	}

	public int getPersonType() {
		return 1;
	}

	@Override
	public boolean getObjectFromDatabase(Connection conn) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean saveObjectToDatabase(Connection conn) {
		java.sql.Statement stmt;
		try {
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			String sql;
			if (this.getId() == 0) {
				sql = "select sts_rollno from st_student where sts_rollno='"
						+ rollNo + "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					// TODO: create error json object and then return
					return false;
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
					java.sql.PreparedStatement pStmt = conn.prepareStatement("insert into st_student (st_id, sts_rollno, sts_deptt, sts_term) values (?, ?, ?, ?)");
					pStmt.setInt(1, id);
					pStmt.setString(2,  this.rollNo);
					pStmt.setInt(3, this.department);
					pStmt.setInt(4,  this.semester);
					pStmt.executeUpdate();
					
				return true;

			} else {
				java.sql.PreparedStatement pStmt = conn.prepareStatement("select stu_id from st_users where stu_id=?");
				pStmt.setInt(1, this.getId());
				rs = pStmt.executeQuery(sql);
				if (rs.next() == false) {
					// TODO: create error json object and then return
					return false;
				}
				pStmt = conn.prepareStatement("update st_users set stu_fname=?, stu_lname=?, stu_email=? where stu_id=?");
				pStmt.setString(1, this.fName);
				pStmt.setString(2, this.lName);
				pStmt.setString(3, this.emailId);
				pStmt.setInt(4, this.getId());
				pStmt.executeUpdate(sql);
				
				pStmt = conn.prepareStatement("update st_student set sts_rollno=?, stu_deptt=? where st_id=?");
				pStmt.setString(1, this.rollNo);
				pStmt.setInt(2, this.department);
				pStmt.setInt(3, this.getId());
				pStmt.executeUpdate(sql);
				
				return true;				
			}
		
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
	public boolean deleteObjectToDatabase(Connection conn) {
		// TODO Auto-generated method stub
		return false;
	}

}
