package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dkt.StudentTimeTable.DBInterface;
import com.dkt.StudentTimeTable.Person;
import com.mysql.jdbc.PreparedStatement;

public class Student extends Person implements DBInterface {

	protected int department;
	protected int semester;
	protected int section;
	protected String rollNo;

	public Student(String fn, String ln, String email,String password, int dpt, int sem,
			int sec, String rn) {
		super(fn, ln, email);
		this.password=password;
		this.department = dpt;
		this.semester = sem;
		this.section = sec;
		this.rollNo = rn;
		this.role = 2;
	}
	public Student(String email) {
		this("", "", email,"", 0, 0, 0, "");
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


	@Override
	public boolean getObjectFromDatabase(Connection conn) throws ProvisionException {
		java.sql.PreparedStatement pStmt;
		try {
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement("select * from st_student join st_users on st_id=stu_id where stu_email=?");
			pStmt.setString(1, emailId);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				this.fName = rs.getString("stu_fname");
				this.lName = rs.getString("stu_lname");
				this.role = rs.getInt("stu_role");
				if(this.role != 2) throw new ProvisionException(2, "Not a Student");
				this.Id = rs.getInt("st_id");
				this.rollNo = rs.getString("sts_rollno");
				this.department = rs.getInt("sts_deptt");
				this.password = rs.getString("stu_password");
				this.section = rs.getInt("sts_section");
				this.semester = rs.getInt("sts_term");
				System.out.println("Deptt: " + this.department +","+this.semester+","+this.section+"," +this.rollNo+",");
			} else {
				throw(new ProvisionException(6, "Student with this email-id does not exist"));
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
				pstmt = conn.prepareStatement("select sts_rollno from st_student where sts_rollno=?");
				pstmt.setString(1, rollNo);
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
				int id = 0;
				if(keys.next()) {
				id = keys.getInt(1);
				} else {
					conn.rollback();
					throw new ProvisionException(10, "Did not get the id of last insert");
				}
				if (id != 0) {
					this.setId(id);
					java.sql.PreparedStatement pStmt = conn.prepareStatement("insert into st_student (st_id, sts_rollno, sts_deptt, sts_term,sts_section) values (?, ?, ?, ?,?)");
					pStmt.setInt(1, id);
					pStmt.setString(2,  this.rollNo);
					pStmt.setInt(3, this.department);
					pStmt.setInt(4,  this.semester);
					pStmt.setInt(5,this.section);
					pStmt.executeUpdate();
				}	
				conn.commit();
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
				
				pStmt = conn.prepareStatement("update st_student set sts_rollno=?, sts_deptt=?, sts_section=?, sts_term=? where st_id=?");
				pStmt.setString(1, this.rollNo);
				pStmt.setInt(2, this.department);
				pStmt.setInt(3, this.getSection());
				pStmt.setInt(4, this.getSemester());
				pStmt.setInt(5, this.getId());
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
	public boolean deleteObjectToDatabase(Connection conn)  throws ProvisionException {
		// TODO Auto-generated method stub
		return false;
	}
}
