package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dkt.StudentTimeTable.DBQueryInterface;
import com.dkt.StudentTimeTable.Student;

public class StudentList implements DBQueryInterface,DBQueryListInterface, JSONable{
	List<Student> listOfStudents ;
	protected int department;
	protected int semester;
	protected int section;
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

	public StudentList(int sec,int dept,int sem){
		this.department=dept;
		this.semester=sem;
		this.section=sec;
		
		
	}
	

	@Override
	public void getListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub

	}

	public boolean getObjectListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub
		java.sql.PreparedStatement pStmt;
		try {
			pStmt = conn.prepareStatement("select * from st_student join st_users on st_id=stu_id where sts_deptt=? and sts_term=? and sts_section=?");
			pStmt.setInt(1,department );
			pStmt.setInt(2,semester );
			pStmt.setInt(3,section );
			if(listOfStudents == null) listOfStudents = new ArrayList<Student>();
			
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
	    		this.department = rs.getInt("sts_deptt");
				this.section = rs.getInt("sts_section");
				this.semester = rs.getInt("sts_term");
				Student std = new Student( rs.getString("stu_fname"), rs.getString("stu_lname"), 
							rs.getString("stu_email"), rs.getString("stu_password"),
							department, semester, section, 
							rs.getString("sts_rollno"));
				listOfStudents.add(std);
//				System.out.println("Deptt: " + this.department +","+this.semester+","+this.section+"," );
			}
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject getJSon() {
		// TODO Auto-generated method stub
		JSONObject js = new JSONObject();
		JSONArray jsa = new JSONArray();
		for(Student s : listOfStudents) {
			JSONObject jsStd = new JSONObject();
			jsStd.put("firstName", s.getfName());
			jsStd.put("lastName", s.getlName());
			jsStd.put("rollNo", s.getRollNo());
			jsStd.put("email", s.getEmailId());
			
			jsa.put(jsStd);
		}
		js.put("studentList", jsa);
		return js;
	}
		
	}


