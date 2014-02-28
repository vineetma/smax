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

import com.dkt.StudentTimeTable.Student;
import com.dkt.StudentTimeTable.StudentDatabase;

/**
 * Servlet implementation class Provision
 */
//@WebServlet("/provision")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String callbackName = request.getParameter("callback");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(callbackName + "("+"{test:'123'}"+")");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputStream is = request.getInputStream();
		// alternatively, one can convert inputstream to string using IOUtils
		//  IOUtils.toString(is, "UTF-8");
		if (is != null) {
			/* lets get input stream reader to get access to the body of the
				request */
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
			/*if (request.getParameter("action").equals("add")) {*/
				if (objStr != null && objStr.length() > 0) {
					JSONObject js = new JSONObject(objStr);
					String action = js.getString("action");
					if(action.equals("add")) {
						JSONObject obj = js.getJSONObject("student");
						String fn = obj.getString("firstName");
						String ln = obj.getString("lastName");
						String email = obj.getString("email");
						int dpt = obj.getInt("department");
						int sem = obj.getInt("semester");
						int sec = obj.getInt("section");
						
						String rn = obj.getString("rollNo");
						
						Student std = new Student(fn, ln, email, dpt, sem, sec, rn);
						//StudentData.writeObject(std);
						boolean ret_val = stdb.writeObject(std);
						/* This part of the code will have the logic of updating database
						 * 
						 */
						PrintWriter pw = response.getWriter();
						response.setContentType("application/json");
						/* Write the status of the processing to this output */
						JSONObject jso = new JSONObject();
						jso.put("status", ret_val);
						jso.put("status_code", 0);
						if(ret_val == true) {
						jso.put("status_message", "Student API success");
						} else {
							jso.put("status_message", "Student API failed");
						}
						pw.print(jso.toString());
					}
				}
		}
	}

}
