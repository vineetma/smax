package com.dkt.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dkt.StudentTimeTable.Person;
import com.dkt.StudentTimeTable.PersonFactory;
import com.dkt.StudentTimeTable.ProvisionException;
import com.dkt.StudentTimeTable.StudentDatabase;
import com.dkt.StudentTimeTable.StudentList;
import com.dkt.StudentTimeTable.TimeTable;
import com.dkt.StudentTimeTable.TimeTables;

/**
 * Servlet implementation class timetable
 */
//@WebServlet("/timetable")
public class Timetable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDatabase stdb = null;
	@Override
	public void init() {
		stdb = new StudentDatabase();
		stdb.setDbUser(getServletContext().getInitParameter("dbUser"));
		stdb.setDbPassword(getServletContext().getInitParameter("dbPassword"));
		stdb.open();

	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Timetable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject jso = new JSONObject();
		response.setContentType("application/json");
		try {
		if (request.getParameter("action") == null)
			return;
		else if (request.getParameter("action").equals("getWeek")) {
			if(request.getParameter("department") != null) {
				int department = Integer.parseInt(request.getParameter("department"));
				int section = Integer.parseInt(request.getParameter("section"));
				int semester = Integer.parseInt(request.getParameter("term"));
					// id is null, as we do not know the timeslot..
			    	TimeTable stdl = new TimeTable(0, section, department, semester);
					stdb.readObject(stdl);
				
				jso.put("status", true);
				jso.put("status_code", 0);
				jso.put("status_message", " API success");
				jso.put("timeSlots", stdl.getJSon());
			}
		} else if(request.getParameter("action").equals("myTimetable")) {
			if(request.getParameter("week")!=null && request.getParameter("userId") != null){
				Person per = PersonFactory.getPersonFromEmail(request.getParameter("userId"));
				
				int week=Integer.parseInt(request.getParameter("week"));
			TimeTables sdt2= new TimeTables(per.getId(),week);
			stdb.readObject(sdt2);
			
			jso.put("status", true);
			jso.put("status_code", 0);
			jso.put("status_message", " API success");
			jso.put("teacherTimetable", sdt2.getJSon());
			
			}
		}
		

			} catch (ProvisionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jso.put("status", false);
				jso.put("status_code", e.getErrorCode());
				jso.put("status_message", e.getErrorMessage());
			}
			
			String callbackName = request.getParameter("callback");
			response.setContentType("application/json");
			out.println(callbackName + "(" + jso.toString() + ")");

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
