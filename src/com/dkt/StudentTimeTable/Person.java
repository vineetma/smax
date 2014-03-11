package com.dkt.StudentTimeTable;

public abstract class Person implements DBInterface {
	protected int Id;
	protected String fName;
	protected String lName;
	protected String emailId;
	protected int role;
	protected String password;
	enum USER_ROLE {ADMIN, STUDENT,TEACHER};
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	public Person(String fn, String ln, String em) {
		this.fName = fn;
		this.lName = ln;
		this.emailId = em;
		this.Id = 0;
		this.password = "";
	}

	public Person(int id, String firstName, String lastName, String email,
			String Password) {
		this.Id = id;
		this.fName = firstName;

		this.lName = lastName;
		this.emailId = email;
		this.password = Password;

	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}

}
