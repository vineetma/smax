package com.dkt.StudentTimeTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONObject;

import com.dkt.StudentTimeTable.DBInterface;

public class Note implements  DBInterface, JSONable {
	protected int stn_id;
	protected int stn_user_id;
	protected int stn_period_id;
	protected String stn_user_notes;
	protected Date stn_dt;
	
	
	public int getStn_id() {
		return stn_id;
	}

	public void setStn_id(int stn_id) {
		this.stn_id = stn_id;
	}

	

	public int getStn_user_id() {
		return stn_user_id;
	}

	public void setStn_user_id(int stn_user_id) {
		this.stn_user_id = stn_user_id;
	}

	public int getStn_period_id() {
		return stn_period_id;
	}

	public void setStn_period_id(int stn_period_id) {
		this.stn_period_id = stn_period_id;
	}

	public String getStn_user_notes() {
		return stn_user_notes;
	}

	public void setStn_user_notes(String stn_user_notes) {
		this.stn_user_notes = stn_user_notes;
	}

	
	public Note(int userId,int periodId,String notes ){
		this.stn_user_id=userId;
		this.stn_period_id= periodId;
		this.stn_user_notes=notes;
		
	}
	public Note(int userId, int periodId ) {
		this(userId, periodId,"");
	}
	


	@Override
	public boolean getObjectFromDatabase(Connection conn){
	java.sql.PreparedStatement pStmt;
	try {
		conn.setAutoCommit(false);
		pStmt = conn.prepareStatement("select * from st_notes   where stn_user_id=? and stn_period_id=? order by stn_id desc");
		pStmt.setInt(1, stn_user_id);
		pStmt.setInt(2, stn_period_id);
		
		ResultSet rs = pStmt.executeQuery();
		if(rs.next()) {
			this.stn_user_notes = rs.getString("stn_user_notes");
			this.stn_dt = rs.getDate("stn_dt");
			this.stn_period_id = rs.getInt("stn_period_id");
			this.stn_user_id = rs.getInt("stn_user_id");
			System.out.println("Deptt: " + this.stn_user_notes +"");
		} else {
			try {
				throw(new ProvisionException(6, "Notes not found"));
			} catch (ProvisionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	return true;
	// TODO Auto-generated method stub

}
	

	@Override
	public boolean saveObjectToDatabase(Connection conn){
	java.sql.PreparedStatement pstmt = null;
	try {
		conn.setAutoCommit(false);
		
		
			pstmt = conn.prepareStatement("insert into st_notes ( stn_user_id, stn_period_id , stn_user_notes) "
					+ " values (?, ?, ?)");
					
					pstmt.setInt(1, this.stn_user_id);
				    pstmt.setInt(2, this.stn_period_id);
					pstmt.setString(3, this.stn_user_notes);
					
			
			pstmt.executeUpdate();
			conn.commit();
			return true;
		
		
		} catch (SQLException ex) {
		ex.printStackTrace();
		try {
			conn.rollback();
			try {
				throw new ProvisionException(4, "DB Error, while working with Student DB");
			} catch (ProvisionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new ProvisionException(4, "DB Error, Could not roll back");
			} catch (ProvisionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	return true;

}


	@Override
	public boolean deleteObjectToDatabase(Connection conn)
			throws ProvisionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONObject getJSon() {
		// TODO Auto-generated method stub
		JSONObject js = new JSONObject();
		
		js.put("userNotes", this.getStn_user_notes());
		js.put("stn_user_id", this.getStn_user_id());
		js.put("stn_period_id", this.getStn_period_id());
		js.put("stn_dt", this.getStn_dt());
		return js;
	}

	public Date getStn_dt() {
		return stn_dt;
	}

	public void setStn_dt(Date stn_dt) {
		this.stn_dt = stn_dt;
	}
}

	
