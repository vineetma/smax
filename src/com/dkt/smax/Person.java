package com.dkt.smax;

import java.util.List;


public abstract class Person {
protected String name;
protected List<String> phones;
protected String password;
protected String email;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
abstract public int getPersonType();
public Person(String name) {
	this.name = name;
}
}