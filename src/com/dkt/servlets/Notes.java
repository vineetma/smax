package com.dkt.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dkt.StudentTimeTable.Note;
import com.dkt.StudentTimeTable.NotesCollection;
import com.dkt.StudentTimeTable.Person;
import com.dkt.StudentTimeTable.PersonFactory;
import com.dkt.StudentTimeTable.ProvisionException;
import com.dkt.StudentTimeTable.Student;
import com.dkt.StudentTimeTable.StudentDatabase;
import com.dkt.StudentTimeTable.TimeTable;

/**
 * Servlet implementation class Student
 */
//@WebServlet("/Student")
public class Notes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDatabase stdb = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notes() {
        super();
        // TODO Auto-generated constructor stub
    }
	@Override
	public void init() {
		stdb = new StudentDatabase();
		stdb.setDbUser(getServletContext().getInitParameter("dbUser"));
		stdb.setDbPassword(getServletContext().getInitParameter("dbPassword"));
		stdb.open();
		PersonFactory.setDB(stdb);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Testing..");
		PrintWriter out = response.getWriter();
		JSONObject jso = new JSONObject();
		response.setContentType("application/json");
		try {
		if (request.getParameter("action") == null)
			return;
		else if (request.getParameter("action").equals("saveNotes")) {
			if(request.getParameter("userId") != null) {
				//TODO: get the user id based on the email id received in the request
				Person per = PersonFactory.getPersonFromEmail(request.getParameter("userId"));
				Note nt=new Note(
						per.getId(),
						Integer.parseInt(request.getParameter("periodId")),
						request.getParameter("userNotes"));
				
				stdb.writeObject(nt);
				
				
				
				
				jso.put("status", true);
				jso.put("status_code", 0);
				jso.put("status_message", " API success");
			
			}}
			else if (request.getParameter("action").equals("readNotes")) {
				Person per = PersonFactory.getPersonFromEmail(request.getParameter("userId"));
				int uId = per.getId();
				int pId=Integer.parseInt(request.getParameter("periodId"));
				
				if (uId != 0 || pId != 0) {
					NotesCollection rn = new NotesCollection(uId,pId);
					stdb.readObject(rn);
									
					jso.put("status", true);
					jso.put("status_code", 0);
					jso.put("status_message", " API success");
					jso.put("notes", rn.getJSon());
				}
		}

			} 
		catch (ProvisionException e) {
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
