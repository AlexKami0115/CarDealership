package com.revature.beans;

public class User {
	Integer ID;
	String username;
	String password;
	String dataAccess;
	
	public User() {
		super();
	}
	
	public User(Integer id, String username, String password, String dataAccess) {
		super();
		this.ID = id;
		this.username = username;
		this.password = password;
		this.dataAccess = dataAccess;
}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
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
		this.password = password;
	}

	public String getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(String dataAccess) {
		this.dataAccess = dataAccess;
	}
		
	
}
