package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotesCollection implements DBInterface, JSONable  {
	protected int stn_user_id;
	protected int stn_period_id;
	protected List<Note> notes;
	public NotesCollection(int uid, int pid) {
		this.stn_user_id = uid;
		this.stn_period_id = pid;
		notes = null;
	}
	@Override
	public JSONObject getJSon() {
		// TODO Auto-generated method stub
		JSONArray jsa = new JSONArray();
		for(Note n : notes) {
			jsa.put(n.getJSon());
		}
		JSONObject js = new JSONObject();
		js.put("notes", jsa);
		return js;
	}
	@Override
	public boolean getObjectFromDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		java.sql.PreparedStatement pStmt;
		try {
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement("select * from st_notes   where stn_user_id=? and stn_period_id=? order by stn_id desc");
			pStmt.setInt(1, stn_user_id);
			pStmt.setInt(2, stn_period_id);
			
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				Note note = new Note(this.stn_user_id, this.stn_period_id);
				note.setStn_user_notes(rs.getString("stn_user_notes"));
				note.setStn_dt(rs.getDate("stn_dt"));
				if(notes == null) notes = new ArrayList<Note>();
				notes.add(note);
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
		} 
		return false;
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
}
