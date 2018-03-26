package org.itstet.model;

public class Account {
	private String name;
	private String surname;
	private String email;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPass(String password) {
		this.password = password;
	}

	public Account(String name, String surname, String email, String pass) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}

}
