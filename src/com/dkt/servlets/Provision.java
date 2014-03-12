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

import com.dkt.StudentTimeTable.Admin;
import com.dkt.StudentTimeTable.ProvisionException;
import com.dkt.StudentTimeTable.Student;
import com.dkt.StudentTimeTable.StudentDatabase;
import com.dkt.StudentTimeTable.StudentList;
import com.dkt.StudentTimeTable.Teacher;

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
		if (request.getParameter("action") == null)
			return;
		try {
			if (request.getParameter("action").equals("add")) {
				if (request.getParameter("student[firstName]") != null) {
					Student std = new Student(
							request.getParameter("student[firstName]"),
							request.getParameter("student[lastName]"),
							request.getParameter("student[email]"),
							request.getParameter("student[password]"),
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
				} else if (request.getParameter("teacher[firstName]") != null) {
					Teacher tch = new Teacher(
							request.getParameter("teacher[firstName]"),
							request.getParameter("teacher[lastName]"),
							request.getParameter("teacher[email]"),
							Integer.parseInt(request
									.getParameter("teacher[department]")),
							request.getParameter("teacher[teacherId]"));

					stdb.writeObject(tch);
					/* Write the status of the processing to this output */
					jso.put("status", true);
					jso.put("status_code", 0);
					jso.put("status_message", "Teacher API success");
				}

			} else if (request.getParameter("action").equals("read")) {
				String email = request.getParameter("email");
				if (email != "" || email != null) {
					Student std = new Student(email);
					stdb.readObject(std);
					JSONObject js2 = new JSONObject();

					js2.put("id", std.getId());
					js2.put("firstName", std.getfName());
					js2.put("lastName", std.getlName());
					js2.put("rollNo", std.getRollNo());
					js2.put("department", std.getDepartment());
					js2.put("section", std.getSection());
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
						request.getParameter("student[password]"),
						Integer.parseInt(request
								.getParameter("student[department]")),
						Integer.parseInt(request
								.getParameter("student[semester]")),
						Integer.parseInt(request
								.getParameter("student[section]")),
						request.getParameter("student[rollNo]"));
				std.setId(Integer.parseInt(request.getParameter("student[id]")));
				stdb.writeObject(std);
				/*
				 * Write the status of the processing to thi s output
				 */
				jso.put("status", true);
				jso.put("status_code", 0);
				jso.put("status_message", "Student API success");
			}

			 else if (request.getParameter("action").equals("read1")) {
				String email = request.getParameter("email");
				if (email != "" || email != null) {
					Teacher tch = new Teacher(email);
					stdb.readObject(tch);
					JSONObject js2 = new JSONObject();

					js2.put("id", tch.getId());
					js2.put("firstName", tch.getfName());
					js2.put("lastName", tch.getlName());
					js2.put("teacherId", tch.getTecherId());
					js2.put("department", tch.getDepartment());

					jso.put("status", true);
					jso.put("status_code", 0);
					jso.put("status_message", "Teacher API success");
					jso.put("teacher", js2);
				}
			} else if (request.getParameter("action").equals("update1")) {
				Teacher tch = new Teacher(
						request.getParameter("teacher[firstName]"),
						request.getParameter("teacher[lastName]"),
						request.getParameter("teacher[email]"),
						Integer.parseInt(request
								.getParameter("teacher[department]")),

						request.getParameter("teacher[teacherId]"));
				tch.setId(Integer.parseInt(request.getParameter("teacher[id]")));
				stdb.writeObject(tch);
				/* Write the status of the processing to this output */
				jso.put("status", true);
				jso.put("status_code", 0);
				jso.put("status_message", "Teacher API success");
			} else if (request.getParameter("action").equals("list")) {
				if(request.getParameter("studentList[department]") != null) {
					int deptt = Integer.parseInt(request.getParameter("studentList[department]"));
					int section = Integer.parseInt(request.getParameter("studentList[section]"));
					int semester = Integer.parseInt(request.getParameter("studentList[semester]"));
					StudentList stdl = new StudentList(section, deptt, semester);
					stdb.readObjectList(stdl);
					jso = stdl.getJSon();
					jso.put("status", true);
					jso.put("status_code", 0);
					jso.put("status_message", "Student List API success");

				}
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
					try {
						JSONObject obj = js.getJSONObject("student");
						String fn = obj.getString("firstName");
						String ln = obj.getString("lastName");
						String email = obj.getString("email");
						int dpt = obj.getInt("department");
						int sem = obj.getInt("semester");
						int sec = obj.getInt("section");
						String pass = obj.getString("password");

						String rn = obj.getString("rollNo");

						Student std = new Student(fn, ln, email, pass, dpt, sem, sec, rn);
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
				// teacher
				if (action.equals("add1")) {
					try {
						JSONObject obj = js.getJSONObject("teacher");
						String fn = obj.getString("firstName");
						String ln = obj.getString("lastName");
						String email = obj.getString("email");
						int dpt = obj.getInt("department");

						String tid = obj.getString("teacherId");

						Teacher tch = new Teacher(fn, ln, email, dpt, tid);
						// StudentData.writeObject(std);
						stdb.writeObject(tch);
						/*
						 * This part of the code will have the logic of updating
						 * database
						 */
						response.setContentType("application/json");
						/* Write the status of the processing to this output */
						jso.put("status", true);
						jso.put("status_code", 0);

						jso.put("status_message", "Teacher API success");

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
