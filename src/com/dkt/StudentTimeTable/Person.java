package com.dkt.StudentTimeTable;

public abstract class Person {
	protected int Id;
	protected String fName;
	protected String lName;
	protected String emailId;
	protected String password;

	public Person(String fn, String ln, String em) {
		this.fName = fn;
		this.lName = ln;
		this.emailId = em;
		this.Id = 0;
		this.password = "";
	}
	abstract public int getPersonType();

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

}
