package com.dkt.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dkt.StudentTimeTable.Admin;
import com.dkt.StudentTimeTable.PersonFactory;
import com.dkt.StudentTimeTable.ProvisionException;
import com.dkt.StudentTimeTable.StudentDatabase;
import com.dkt.smax.Person;

/**
 * Servlet implementation class Login
 */
//@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	StudentDatabase stdb = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		PrintWriter out = response.getWriter();
		JSONObject jso = new JSONObject();
		response.setContentType("application/json");
		if (request.getParameter("action") == null)
			return;
		try {
			if (request.getParameter("action").equals("validateUser")) {
				com.dkt.StudentTimeTable.Person person = PersonFactory.getPersonFromEmail(request.getParameter("user_login"));
				if(person == null) throw new ProvisionException (4, "User does not exist");
				
				if(person.getPassword().equals(request.getParameter("user_password"))) {
					jso.put("status", true);
					jso.put("status_code", 0);
					jso.put("status_message", "User logged in successfully");
					jso.put("user_role", person.getRole());
				} else throw(new ProvisionException(2, "Invalid username or password"));			
			}
		} catch(ProvisionException e) {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
