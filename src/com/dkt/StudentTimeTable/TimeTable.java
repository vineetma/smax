package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dkt.StudentTimeTable.DBInterface;
import com.dkt.StudentTimeTable.DBQueryInterface;
import com.dkt.StudentTimeTable.Subject;
import com.dkt.StudentTimeTable.Teacher;

public class TimeTable implements DBQueryInterface, DBInterface, JSONable {
	List<Timeslot> listOfTimeTable = null;
	protected int department;
	protected int semester;
	protected int section;
	protected Date startDate;
	protected int week;
	protected int id;
	private int teacherId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public TimeTable( Date startDate, int i, int department, int section, int semester, int week) {
		this.id = i;
		this.department = department;
		this.section = section;
		this.semester = semester;
		this.week = week;
		this.startDate = startDate;
		listOfTimeTable = new ArrayList<Timeslot>();
	}
	public TimeTable(Date startDate,int i, int department, int section, int semester) {
		this(startDate, i, department, section, semester,0);
	}

	@Override
	public boolean getObjectFromDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		java.sql.PreparedStatement pStmt;
		try {
			conn.setAutoCommit(false);
			String sql = null;
			String whereStr = null;
			if(department != 0 && section != 0 && semester != 0&&startDate != null)
				whereStr = "where sttb_department='"+department+"' and sttb_term='"+semester+"' and sttb_section='"+section+"'and stts_date='"+startDate+"' order by stts_week, stts_day,stts_number;";
			else if(startDate != null && teacherId != 0 && id != 0) {
				whereStr = "where stts_date='"+startDate+"' and sts_teacher_id='"+teacherId+"' and sttb_id='"+id+"' order by  stts_number;";				
			} else if(id != 0) {
				whereStr = "where sttb_id='"+id+"' order by stts_week, stts_day,stts_number;";
			} else whereStr = "where 1";
			 sql = "select stts_id, sts_subject_id, stb_name, sts_teacher_id, stu_fname, stts_date,stu_lname, stts_number, stts_room, stts_week, stts_day, stts_subject_teacher_id "
				+ " from st_timetable_slots "
				+ " left join st_subject_teacher a on a.stst_id=stts_subject_teacher_id  "
				+ " left join st_subjects on stb_id=sts_subject_id "
				+ " left join st_timetable on sttb_id=stts_tt_id"
				+ " left join st_users on sts_teacher_id=stu_id "+whereStr;
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Timeslot tt2 = new Timeslot(rs.getDate("stts_date"), rs.getInt("stts_id"),
						rs.getInt("stts_week"), rs.getInt("stts_day"),
						rs.getInt("stts_number"),
						new SubjectTeacher(rs.getInt("stts_subject_teacher_id"), 
								new Subject(rs.getInt("sts_subject_id"), rs.getString("stb_name")), 
								new Teacher(rs.getString("stu_fname"), rs.getString("stu_lname"), null, null, 0, rs.getInt("sts_teacher_id"))),
						rs.getInt("stts_room"));

				listOfTimeTable.add(tt2);
			}
			if(id != 0)
				whereStr = " where sttb_id='"+id+"'";
			else
				whereStr = " where sttb_department='"+department+"' and sttb_term='"+semester+"' and sttb_section='"+section+"'";

			sql = "select sttb_department, sttb_term, sttb_section from st_timetable "+whereStr;

			pStmt = conn.prepareStatement(sql);

			ResultSet rs1 = pStmt.executeQuery();
			if(rs1.next()){
				department = rs1.getInt("sttb_department");
				semester = rs1.getInt("sttb_term");
				section=   rs1.getInt("sttb_section");
				
			}
			return false;

		} 
		catch (SQLException ex) {
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
	public void getListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub

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

	public JSONObject getJSon() {
		// TODO Auto-generated method stub
		JSONObject js = new JSONObject();
		int week = 0, lastWeek = 0;
		int day = 0, lastDay = 0;
		JSONArray jsaDayTs = new JSONArray();
		JSONArray jsaWeekDays = new JSONArray();
		JSONArray jsaTimetableWeeks = new JSONArray();
		for (Timeslot ts : listOfTimeTable) {
			lastDay = day;
			day = ts.getDay();
			lastWeek = week;
			week = ts.getWeek();
			JSONObject jsSub = new JSONObject(ts.getSubjectTeacher().getSubject());
			JSONObject jsTchr = new JSONObject(ts.getSubjectTeacher().getTeacher());
			JSONObject jsST = new JSONObject();
			jsST.put("id", ts.getSubjectTeacher().getId());
			jsST.put("subject", jsSub);
			jsST.put("teacher", jsTchr);
			JSONObject jsTs = new JSONObject(ts);
			jsTs.put("subjectTeacher", jsST);
			jsaDayTs.put(jsTs);
			if (day != lastDay && lastDay != 0) {
				//TODO: Generate the date relevant for this day.
				// by doing start date + week * day
				jsaWeekDays.put(jsaDayTs);
				jsaDayTs = new JSONArray();
			}
			if (week != lastWeek && lastWeek != 0) {
				jsaTimetableWeeks.put(jsaWeekDays);
				jsaWeekDays = new JSONArray();
			}
		}
		if (week != 0) {
			jsaWeekDays.put(jsaDayTs);
			jsaTimetableWeeks.put(jsaWeekDays);
		}

		js.put("timetable", jsaTimetableWeeks); //only slots here
		JSONObject jsTimeTableObject = new JSONObject();
		jsTimeTableObject.put("department", department);
		jsTimeTableObject.put("section", section);
		jsTimeTableObject.put("semester",semester );
		jsTimeTableObject.put("startDate", startDate);
		
		js.put("timetableObject", jsTimeTableObject); //only department, star date etc
		return js;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

}
