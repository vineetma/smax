package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TimeTables implements DBQueryInterface, DBInterface, JSONable {
	int teacherId=0;
	int week = 0;
	protected int semester;
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
	protected int section;
	protected List<TimeTable> timeTables = null;
	public TimeTables() {
		teacherId = 0;
		week = 0;
	}
	public TimeTables (int tid, int wk){
		teacherId = tid; week = wk;
	}
	@Override
	public JSONObject getJSon() {
		// TODO Auto-generated method stub
		JSONArray jsa = new JSONArray();
		for(TimeTable tt:timeTables){
			jsa.put(tt.getJSon());
		}
		JSONObject js = new JSONObject();
		js.put("timeTables", jsa);
		return js;
	}
	@Override
	public boolean getObjectFromDatabase(Connection conn)
		// TODO Auto-generated method stub
		throws ProvisionException {
			// TODO Auto-generated method stub
			java.sql.PreparedStatement pStmt;
			try {
				conn.setAutoCommit(false);
				String whereStr = null;
				if(week != 0 && teacherId != 0) {
					whereStr = "where stts_week='"+week+"' and sts_teacher_id='"+teacherId+"' order by stts_week, stts_day;";				
				}
				String sql = "select DISTINCT sttb_id "
					+ " from st_timetable_slots "
					+ " left join st_subject_teacher a on a.stst_id=stts_subject_teacher_id  "
					+ " left join st_subjects on stb_id=sts_subject_id "
					+ " left join st_timetable on sttb_id=stts_tt_id"
					+ " left join st_users on sts_teacher_id=stu_id "+whereStr;
				pStmt = conn.prepareStatement(sql);
				if(timeTables == null) timeTables = new ArrayList<TimeTable>();
				ResultSet rs = pStmt.executeQuery();
				while (rs.next()) {
					int timeTableId = rs.getInt("sttb_id");
					TimeTable tt = new TimeTable(timeTableId, 0, 0, 0);
					tt.setTeacherId(teacherId);
					tt.setWeek(week);
				
		
					tt.getObjectFromDatabase(conn);
					timeTables.add(tt);
				}
			}catch (SQLException ex) {
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
		
		return true;
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
	public void getListFromDatabase(Connection conn) {
		// TODO Auto-generated method stub
		
	}
	
}
