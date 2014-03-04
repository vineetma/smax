package com.dkt.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dkt.StudentTimeTable.ProvisionException;
import com.dkt.StudentTimeTable.Student;
import com.dkt.StudentTimeTable.StudentDatabase;

/**
 * Servlet implementation class Provision
 */
// @WebServlet("/provision")
public class Provision extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDatabase stdb = null;

	@Override
	public void init() {
		stdb = new StudentDatabase();
		stdb.open();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Provision() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject jso = new JSONObject();
		response.setContentType("application/json");
		if(request.getParameter("action") == null) return;
		try {
		if (request.getParameter("action").equals("add")) {
				Student std = new Student(
						request.getParameter("student[firstName]"),
						request.getParameter("student[lastName]"),
						request.getParameter("student[email]"),
						Integer.parseInt(request
								.getParameter("student[department]")),
						Integer.parseInt(request
								.getParameter("student[semester]")),
						Integer.parseInt(request
								.getParameter("student[section]")),
						request.getParameter("student[rollNo]"));
							
				stdb.writeObject(std);
				/* Write the status of the processing to this output */
				jso.put("status", true);
				jso.put("status_code", 0);
				jso.put("status_message", "Student API success");
			

		} else if (request.getParameter("action").equals("read")) {
			String rollNo = request.getParameter("rollNo");
			if(rollNo != "" || rollNo != null) {
				Student std = new Student(rollNo);
				stdb.readObject(std);
				JSONObject js2 = new JSONObject();
				
				js2.put("id", std.getId());
				js2.put("firstName", std.getfName());
				js2.put("lastName", std.getlName());
				js2.put("email",  std.getEmailId());
				js2.put("department",  std.getDepartment());
				js2.put("section",  std.getSection());
				js2.put("semester", std.getSemester());
				jso.put("status", true);
				jso.put("status_code", 0);
				jso.put("status_message", "Student API success");
				jso.put("student", js2);
			}
		} else if (request.getParameter("action").equals("update")) {
			Student std = new Student(
					request.getParameter("student[firstName]"),
					request.getParameter("student[lastName]"),
					request.getParameter("student[email]"),
					Integer.parseInt(request
							.getParameter("student[department]")),
					Integer.parseInt(request
							.getParameter("student[semester]")),
					Integer.parseInt(request
							.getParameter("student[section]")),
					request.getParameter("student[rollNo]"));
			std.setId(Integer.parseInt(request.getParameter("student[id]")));
			stdb.writeObject(std);
			/* Write the status of the processing to this output */
			jso.put("status", true);
			jso.put("status_code", 0);
			jso.put("status_message", "Student API success");			
		}
		} catch (ProvisionException e) {
			// TODO Auto-generated catch block
			jso.put("status", false);
			jso.put("status_code", e.getErrorCode());
			jso.put("status_message", e.getErrorMessage());
		}
		String callbackName = request.getParameter("callback");
		response.setContentType("application/json");
		out.println(callbackName + "(" + jso.toString() + ")");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputStream is = request.getInputStream();
		// alternatively, one can convert inputstream to string using IOUtils
		// IOUtils.toString(is, "UTF-8");
		if (is != null) {
			/*
			 * lets get input stream reader to get access to the body of the
			 * request
			 */
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String objStr = null;
			String s = null;
			/* Retrieve the whole of string */
			while ((s = br.readLine()) != null) {
				if (objStr == null)
					objStr = s;
				else
					objStr = objStr + s;
			}
			/* if (request.getParameter("action").equals("add")) { */
			if (objStr != null && objStr.length() > 0) {
				JSONObject js = new JSONObject(objStr);
				String action = js.getString("action");
				JSONObject jso = new JSONObject();
				PrintWriter pw = response.getWriter();
				if (action.equals("add")) {
					try{
					JSONObject obj = js.getJSONObject("student");
					String fn = obj.getString("firstName");
					String ln = obj.getString("lastName");
					String email = obj.getString("email");
					int dpt = obj.getInt("department");
					int sem = obj.getInt("semester");
					int sec = obj.getInt("section");

					String rn = obj.getString("rollNo");

					Student std = new Student(fn, ln, email, dpt, sem, sec, rn);
					// StudentData.writeObject(std);
					stdb.writeObject(std);
					/*
					 * This part of the code will have the logic of updating
					 * database
					 */
					response.setContentType("application/json");
					/* Write the status of the processing to this output */
					jso.put("status", true);
					jso.put("status_code", 0);
					
						jso.put("status_message", "Student API success");
					
					} catch (ProvisionException e) {
						jso.put("status", false);
						jso.put("status_code", 0);
						jso.put("status_message", e.getErrorMessage());
					}
					pw.print(jso.toString());
				}
			}
		}
	}

}
