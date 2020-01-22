package com.anaem.xpulsebo.model;

import org.apache.commons.codec.digest.DigestUtils;

public class User {
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.sha512Hex(password);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "\"" + this.username + "\":{\n" + 
				"{id: " + this.id + "},\n" +
				"{firstname: " + this.firstname + "},\n" +
				"{lastname: " + this.lastname + "},\n" +
				"{role: " + this.role + "},\n}";
	}

}
